package com.platform.application.utils;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * 日期时间格式工具类.
 */
public final class DataFormatUtils {

	/**
	 * 构造函数.
	 */
	private DataFormatUtils() {

	}

	/**
	 * 获取日期时间格式字符串(yyyy-MM-dd HH:mm:ss).
	 *
	 * @param date 日期时间
	 * @return 日期时间格式字符串(yyyy-MM-dd HH:mm:ss)
	 */
	public static String getDateTime(final Date date) {
		if (date != null) {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		}
		return "";
	}

	/**
	 * 获取日期时间格式字符串(yyyy-MM-dd).
	 *
	 * @param date 日期时间
	 * @return 日期时间格式字符串(yyyy-MM-dd)
	 */
	public static String getDate(final Date date) {
		if (date != null) {
			return new SimpleDateFormat("yyyy-MM-dd").format(date);
		}
		return "";
	}

	/**
	 * 获取日期时间格式字符串(yyyyMMddHHmmss).
	 *
	 * @param date 日期时间
	 * @return 日期时间格式字符串(yyyyMMddHHmmss)
	 */
	public static String getPostfix(final Date date) {
		if (date != null) {
			return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
		}
		return "";
	}

	/**
	 * 设置日期中的时间为0点0分0秒.
	 *
	 * @param date 日期时间
	 * @return 时间为0点0分0秒的日期
	 */
	public static Date trimTime(final Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取传入日期的下一个日期，并设置时间为0点0分0秒.
	 *
	 * @param date 日期时间
	 * @return 时间为0点0分0秒的下一日期
	 */
	public static Date getNextDay(final Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(trimTime(date));
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}

	/**
	 * 获取传入日期的下一个间隔日期，并设置时间为0点0分0秒.
	 *
	 * @param date     日期时间
	 * @param interval 间隔天数，可以是正数或负数
	 * @return 时间为0点0分0秒的下一间隔日期
	 */
	public static Date getIntervalDay(final Date date,
			final int interval) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(trimTime(date));
		calendar.add(Calendar.DATE, interval);
		return calendar.getTime();
	}

	/**
	 * 获取真实IP地址;
	 */
	public static String getIpAddr(final HttpServletRequest request) throws Exception {
		String ipAddress = request.getHeader("x-forwarded-for");
		if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
			ipAddress = request.getRemoteAddr();
			if ((ipAddress.equals("127.0.0.1")) || (ipAddress.equals("0:0:0:0:0:0:0:1"))) {
				InetAddress inet = null;
				inet = InetAddress.getLocalHost();
				ipAddress = inet.getHostAddress();
			}
		}
		if ((ipAddress != null) && (ipAddress.length() > 15) &&
				(ipAddress.indexOf(",") > 0)) {
			ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
		}
		return ipAddress;
	}
}
