package com.platform.application.common.cache.jgroups;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

import org.jgroups.Address;
import org.jgroups.Channel;
import org.jgroups.Message;
import org.jgroups.View;
import org.jgroups.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ehcache.Element;
import net.sf.ehcache.distribution.CachePeer;

/**
 * Handles {@link CachePeer}functions around a JGroups {@link Channel} and a
 * {@link net.sf.ehcache.CacheManager}.
 *
 * @author Eric Dalquist
 * @version $Revision$
 */
public class JGroupsCachePeer implements CachePeer {
	/**
	 * The org.slf4j.Logger interface.
	 */
	private static final Logger LOG =
			LoggerFactory.getLogger(JGroupsCachePeer.class.getName());
	/**
	 * The chunk size.
	 */
	private static final int CHUNK_SIZE = 100;
	/**
	 * A channel represents a group communication endpoint
	 * (like BSD datagram sockets).
	 */
	private final Channel channel;
	/**
	 *
	 */
	private final ConcurrentMap<Long, Queue<JGroupEventMessage>>
	asyncReplicationQueues =
	new ConcurrentHashMap<Long, Queue<JGroupEventMessage>>();
	/**
	 * A facility for threads to schedule tasks for future execution in a
	 * background thread.
	 */
	private final Timer timer;
	/**
	 * whether or not to be alive.
	 */
	private volatile boolean alive;

	/**
	 * Create a new {@link CachePeer}.
	 *
	 * @param channelVal  a group communication endpoint
	 * @param clusterName the cluster name
	 */
	public JGroupsCachePeer(
			final Channel channelVal, final String clusterName) {
		this.channel = channelVal;
		this.alive = true;
		this.timer = new Timer(clusterName + " Async Replication Thread", true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void send(final List eventMessages) throws RemoteException {
		this.send(null, eventMessages);
	}

	/* ********** Local Methods ********** */

	/**
	 * @return Get the address of all members in the cluster
	 */
	public List<Address> getGroupMembership() {
		final View view = this.channel.getView();
		return view.getMembers();
	}

	/**
	 * @return Get the address of all members in the cluster other than this one
	 */
	public List<Address> getOtherGroupMembers() {
		final Address localAddress = this.getLocalAddress();
		final List<Address> members = this.getGroupMembership();

		final List<Address> addresses =
				new ArrayList<Address>(members.size() - 1);
		for (final Address member : members) {
			if (!member.equals(localAddress)) {
				addresses.add(member);
			}
		}

		return addresses;
	}

	/**
	 * @return Get the address of this machine in the cluster
	 */
	public Address getLocalAddress() {
		return this.channel.getAddress();
	}

	/**
	 * Shutdown the cache peer.
	 */
	public void dispose() {
		this.alive = false;

		disposeTimer();

		this.flushAllQueues();

		this.asyncReplicationQueues.clear();
	}

	/**
	 * Dispose the timer.
	 */
	private void disposeTimer() {
		// cancel the timer and all tasks
		if (timer != null) {
			timer.cancel();
			timer.purge();
		}
	}

	/**
	 * Sends a list of {@link JGroupEventMessage}s to the specified address,
	 * if no address is set the messages are sent to the entire group.
	 *
	 * @param dest          Address
	 * @param eventMessages JGroupEventMessage
	 */
	public void send(final Address dest,
			final List<JGroupEventMessage> eventMessages) {
		if (!this.alive || eventMessages == null || eventMessages.isEmpty()) {
			if (eventMessages == null) {
				LOG.warn("Ignoring send request of {} messages. "
						+ "Replicator alive = {}", null, this.alive);
			} else {
				LOG.warn("Ignoring send request of {} messages. "
						+ "Replicator alive = {}",
						eventMessages.size(), this.alive);
			}

			return;
		}

		//Iterate over messages, queuing async messages
		// then sending sync messages immediately
		final LinkedList<JGroupEventMessage> synchronousEventMessages =
				new LinkedList<JGroupEventMessage>();
		for (final JGroupEventMessage groupEventMessage : eventMessages) {
			if (groupEventMessage.isAsync()) {
				final long asyncTime = groupEventMessage.getAsyncTime();
				final Queue<JGroupEventMessage> queue =
						this.getMessageQueue(asyncTime);

				queue.offer(groupEventMessage);
				LOG.trace("Queued {} for asynchronous sending.",
						groupEventMessage);
			} else {
				synchronousEventMessages.add(groupEventMessage);
				LOG.trace("Sending {} synchronously.", groupEventMessage);
			}
		}

		//If there are no synchronous messages queued return
		if (synchronousEventMessages.size() == 0) {
			return;
		}

		LOG.debug("Sending {} JGroupEventMessages synchronously.",
				synchronousEventMessages.size());
		this.sendData(dest, synchronousEventMessages);
	}

	/**
	 * @param asyncTime asyncTime
	 * @return JGroupEventMessage
	 */
	private Queue<JGroupEventMessage> getMessageQueue(final long asyncTime) {
		Queue<JGroupEventMessage> queue =
				this.asyncReplicationQueues.get(asyncTime);
		if (queue == null) {
			final Queue<JGroupEventMessage> newQueue =
					new ConcurrentLinkedQueue<JGroupEventMessage>();
			queue = this.asyncReplicationQueues
					.putIfAbsent(asyncTime, newQueue);
			if (queue == null) {
				LOG.debug("Created asynchronous message queue for {}ms period",
						asyncTime);
				//New queue, setup a new timer to flush the queue
				final AsyncTimerTask task = new AsyncTimerTask(newQueue);
				timer.schedule(task, asyncTime, asyncTime);

				return newQueue;
			}
		}
		return queue;
	}

	/**
	 * Sends a serializable object to the specified address.
	 * If no address is specified it is sent to the entire group.
	 *
	 * @param dest     the specified address
	 * @param dataList a serializable object
	 */
	private void sendData(final Address dest,
			final List<? extends Serializable> dataList) {
		//Remove the list wrapper if only a single event is being sent
		final Serializable toSend;
		if (dataList.size() == 1) {
			toSend = dataList.get(0);
		} else {
			toSend = (Serializable) dataList;
		}

		//Serialize the data into a byte[] for sending
		final byte[] data;
		try {
			data = Util.objectToByteBuffer(toSend);
		} catch (Exception e) {
			LOG.error("Error serializing data, it will not be sent: "
					+ toSend, e);
			return;
		}

		//Send it off to the group
		final Message msg = new Message(dest, null, data);
		try {
			this.channel.send(msg);
		} catch (IllegalStateException e) {
			LOG.error("Failed to send message(s) due to the channel "
					+ "being disconnected or closed: "
					+ toSend, e);
		} catch (Exception e) {
			LOG.error("Failed to send message(s) : " + toSend, e);
		}
	}

	/**
	 * Flush all queues.
	 */
	private void flushAllQueues() {
		for (final Queue<JGroupEventMessage> queue
				: this.asyncReplicationQueues.values()) {
			this.flushQueue(queue);
		}
	}

	/**
	 * Flush the queue.
	 *
	 * @param queue queue
	 */
	private void flushQueue(final Queue<JGroupEventMessage> queue) {
		final ArrayList<JGroupEventMessage> events =
				new ArrayList<JGroupEventMessage>(CHUNK_SIZE);
		while (!queue.isEmpty()) {
			events.clear();
			while (!queue.isEmpty() && events.size() < CHUNK_SIZE) {
				final JGroupEventMessage event = queue.poll();
				if (event == null) {
					break;
				}
				if (event.isValid()) {
					events.add(event);
				} else {
					LOG.warn("Collected soft reference during asynchronous "
							+ "queue flush, this event will"
							+ " not be replicated: " + event);
				}
			}
			LOG.debug("Sending {} JGroupEventMessages from the asynchronous"
					+ " queue.", events.size());
			this.sendData(null, events);
		}
	}

	/**
	 * TimerTask that flushes the specified queue when called.
	 */
	private final class AsyncTimerTask extends TimerTask {
		/**
		 * A collection designed for holding elements prior to processing.
		 */
		private final Queue<JGroupEventMessage> queue;

		/**
		 * @param newQueue queue
		 */
		private AsyncTimerTask(final Queue<JGroupEventMessage> newQueue) {
			this.queue = newQueue;
		}

		@Override
		public void run() {
			if (!alive) {
				this.cancel();
				return;
			}

			flushQueue(this.queue);

			if (!alive) {
				this.cancel();
			}
		}
	}

	/* ********** CachePeer Unused ********** */

	@SuppressWarnings("rawtypes")
	@Override
	public List<?> getElements(final List keys) throws RemoteException {
		//Not Implemented
		return null;
	}

	@Override
	public String getGuid() throws RemoteException {
		//Not Implemented
		return null;
	}

	@Override
	public List<?> getKeys() throws RemoteException {
		//Not Implemented
		return null;
	}

	@Override
	public String getName() throws RemoteException {
		//Not Implemented
		return null;
	}

	@Override
	public Element getQuiet(final Serializable key) throws RemoteException {
		//Not Implemented
		return null;
	}

	@Override
	public String getUrl() throws RemoteException {
		//Not Implemented
		return null;
	}

	@Override
	public String getUrlBase() throws RemoteException {
		//Not Implemented
		return null;
	}

	@Override
	public void put(final Element element)
			throws IllegalArgumentException, IllegalStateException,
			RemoteException {
		//Not Implemented
	}

	@Override
	public boolean remove(final Serializable key)
			throws IllegalStateException, RemoteException {
		//Not Implemented
		return false;
	}

	@Override
	public void removeAll() throws RemoteException, IllegalStateException {
		//Not Implemented
	}
}
