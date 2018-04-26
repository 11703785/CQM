package com.platform.application.sysmanage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.platform.application.sysmanage.domain.OptResource;
import com.platform.application.sysmanage.service.OptResourceService;
import com.platform.application.sysmanage.vo.TreeNode;
import com.platform.framework.common.util.JsonUtil;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.dao.impl.GenericHibernateDao;

@Service
public class OptResourceServiceImpl extends GenericHibernateDao<OptResource,String> implements OptResourceService {

	private static final Logger logger = Logger.getLogger(OptResourceServiceImpl.class);
    //新建资源
	public void txCreateOptResource(OptResource optRes) throws Exception {
		if(optRes.getParent()!=null && StringUtil.isEmpty(optRes.getParent().getResId())){
			optRes.setParent(null);
		}
		optRes.setResId(null);
		this.save(optRes);
	}
    //更新资源
	public void txUpdateOptResource(OptResource optRes) throws Exception {
		OptResource tmp = (OptResource) this.load(optRes.getResId());
		tmp.setResName(optRes.getResName());
		tmp.setResCode(optRes.getResCode());
		tmp.setResType(optRes.getResType());
		tmp.setResOrder(optRes.getResOrder());
		tmp.setActions(optRes.getActions());
		tmp.setResUrl(optRes.getResUrl());
		this.update(tmp);
	}
    //删除资源
	public void txDeleteOptResource(OptResource optRes) throws Exception {
		this.delete(optRes);
	}
	//获得所有根节点
	public List getAllRootNode() throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("from OptResource res where res.parent is null order by res.resOrder ASC");
		return this.find(sb.toString());
	}
	//获得所有子节点
	public List getChildren(String pid) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("from OptResource res where res.parent.id=? order by res.resOrder ASC");
		List paras = new ArrayList(1);
		paras.add(pid);
		return this.find(sb.toString(), paras);
	}
	//资源树
	public String getResourcesJson(String pid) throws Exception{
		TreeNode node = null;
		OptResource res = null;
		if(pid==null){
			node = new TreeNode();
			node.setId("-1");
			node.setText("资源树");
			node.setIsParent("true");
			node.setHasChild("true");
			node.setState("closed");
			return "["+JsonUtil.fromObject(node)+"]";
		}else{
			List children = null;
			if(pid.equals("-1")) children = getAllRootNode();
			else children = getChildren(pid);
			List list = new ArrayList();
			for (int i = 0; i < children.size(); i++) {
				res = (OptResource) children.get(i);
				node = new TreeNode();
				node.setText(res.getResName());
				node.setId(res.getResId());
				if ("01".equals(res.getResType())) {
					node.setState("closed");
				}else{
					node.setState("open");
				}
				if (this.hasChildOpt(res.getResId())) {
					node.setIsParent("true");
					node.setHasChild("true");
				}else{
					node.setIsParent("false");
					node.setHasChild("false");
				}
				list.add(node);
			}
			return JsonUtil.fromCollections(list);
		}
	}
	//是否有子资源
	public boolean hasChildOpt(String id){
		boolean result = false;
		String sql = "select opt from OptResource opt where opt.parent.resId = '"+id+"'";
		List list = this.find(sql);
		if(list.size()>0){
			result = true;
		}
		
		return result;
	}
    //根据ID获得资源
	@Override
	public List<OptResource> getOptByID(String id) {
		StringBuffer sb = new StringBuffer();
		sb.append("from OptResource res where res.resId=?");
		List paras = new ArrayList(1);
		paras.add(id);
		return this.find(sb.toString(), paras);
		
	}

}