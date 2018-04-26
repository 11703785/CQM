package com.platform.application.rateindex.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.platform.application.rateindex.domain.RateIndex;
import com.platform.application.rateindex.service.RateIndexService;
import com.platform.application.sysmanage.domain.Person;
import com.platform.application.sysmanage.vo.TreeNode;
import com.platform.framework.common.util.JsonUtil;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.dao.impl.GenericHibernateDao;

@Service
public class RateIndexServiceImpl extends GenericHibernateDao<RateIndex,String> implements RateIndexService {

	public String loadRateIndex(String id, Person person) throws Exception {
		List rateIndexs = new ArrayList();
		List<TreeNode> list = new ArrayList<TreeNode>();
		TreeNode node = null;
		if(StringUtil.isBlank(id)){
			node = new TreeNode();
			node.setId("-1");
			node.setName("指标树");
			node.setIsParent("true");
			node.setHasChild("true");
			return "["+JsonUtil.fromObject(node)+"]";
		}
		rateIndexs = this.getAllRateIndex(id);
		for (int i = 0; i < rateIndexs.size(); i++) {
			RateIndex rateIndex = (RateIndex)rateIndexs.get(i);
			node = new TreeNode();
			node.setName(rateIndex.getName());
			node.setId(rateIndex.getId());
			
			if(this.isHasChild(rateIndex.getId())){
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
	
	/**
	 * 获得所有的指标
	 */
	private List getAllRateIndex(String id) throws Exception{
		String sql = "select i from RateIndex i where 1=1 ";
		if("-1".equals(id)){
			sql += " and i.parent = null  order by i.orderNum asc";
		}else{
			sql += " and i.parent.id = '"+id+"' order by i.orderNum asc";
		}
		List lists = this.find(sql);
		return lists;
	}
	/**
	 * 获得根
	 */
	public List getRoot(){
		List lists = new ArrayList();
		String sql = "from RateIndex where parent.id = '0' ";
		lists = this.find(sql);
		return lists;
	}
	
	/**
	 * 判断是否有子指标
	 */
	private boolean isHasChild(String id)  throws Exception{
		boolean msg = false;
		String sql = "select i from RateIndex i where i.parent.id = '"+id+"' order by i.orderNum asc";
		List list = this.find(sql);
		if(list.size()>0){
			msg = true;
		}
		return msg;
	}
	
	public void txSaveRateIndex(RateIndex rateIndex,Person person,HttpServletRequest request) throws Exception {
		RateIndex parent = null;
		if(StringUtil.isNotBlank(rateIndex.getId())){		
			parent = rateIndex;
		}
		int a;
		if(StringUtil.isNotBlank(rateIndex.getId())){
			String levles = rateIndex.getLevels();
			a = Integer.valueOf(levles);
			a = a+1;
		}else{
			a = 1;
		}
		String numStr=request.getParameter("num");
		int num=StringUtil.isNotBlank(numStr)?Integer.valueOf(numStr):65;
		String temp="A";
	
		//ArrayList<RateIndex> ls=new ArrayList<RateIndex>();
		for(int x=65;x<num;x++){
			temp = String.valueOf((char)x);
			String fName = request.getParameter(temp+"0");
			boolean flag = isRepeat(rateIndex,fName);
			if(flag){
				String code =request.getParameter(temp+"1");
				String indexValues = request.getParameter(temp+"2");
				String indexvtype = request.getParameter(temp+"3");
				String orderNum = request.getParameter(temp+"4");
				rateIndex=new RateIndex();
				rateIndex.setPersonId(person.getPersonId());
				rateIndex.setOrgId(person.getDepartment().getDeptId());
				rateIndex.setCreateTime(new Date());
				rateIndex.setName(fName);
				rateIndex.setCode(code);
				rateIndex.setIndexvtype(indexvtype);
				rateIndex.setIndexValues(indexValues);
				rateIndex.setParent(parent);
				rateIndex.setLevels(String.valueOf(a));
				rateIndex.setOrderNum(Integer.parseInt(orderNum));
				rateIndex.setIndexstatus("1");
				//ls.add(rateIndex);
			}else{
				throw new Exception("同级下存在同名的指标名称！");
			}
			this.save(rateIndex);
		}
	}
	public boolean isRepeat(RateIndex rateIndex,String name){
		boolean flag = true;
		String sql = "";
		List list = new ArrayList();
		if(StringUtil.isNotBlank(rateIndex.getId())){
			sql = "from RateIndex r where r.parent='"+rateIndex.getId()+"' and r.name='"+name+"' " ;
			list = this.find(sql);
		}else{
			sql = "from RateIndex r where r.parent is null and r.name='"+name+"'";
			list = this.find(sql);
		}
		if(list.size() > 0){
			flag = false;
		}else{
			flag = true;
		}
		return flag;
}
	/**
	 * 判断是否有重复
	 */
		public void verifyRateIndex(RateIndex rateIndex) throws Exception {
			StringBuffer sb = new StringBuffer();
			sb.append("from RateIndex a where (a.parent.id=? or a.parent.id is null) and a.name=?");
			List param = new ArrayList(2);
			param.add(rateIndex.getParent().getId());
			param.add(rateIndex.getName());
			if(!StringUtil.isEmpty(rateIndex.getId())){
				sb.append(" and a.id != ?");
				param.add(rateIndex.getId());
			}
				List result = this.find(sb.toString(), param);
				if (result.size() > 0) {
					throw new Exception("同级下存在重名的指标名称！");
				}
		}
	public void txDeleteAll(String id) throws Exception {
		this.deleteByKey(id);
	}

	public List findRateIndex(String id) throws Exception {
		List list = new ArrayList();
		String sql = "from RateIndex where id='"+id+"'";
		list = this.find(sql);
		return list;
	}

	public void txUpdate(RateIndex rateIndex,String types) throws Exception {
		RateIndex tmp = (RateIndex) this.load(rateIndex.getId());
		verifyRateIndex(rateIndex);
		tmp.setName(rateIndex.getName());
		tmp.setCode(rateIndex.getCode());
		tmp.setOrderNum(rateIndex.getOrderNum());
		tmp.setIndexValues(rateIndex.getIndexValues());
		tmp.setIndexvtype(types);
		this.update(tmp);
	}

	public List getRateIndexOptios(String id) throws Exception {
		List lists = new ArrayList();
		String sql = "from RateIndexOptions where rateIndex.id ='"+id+"'";
		lists = this.find(sql);
		return lists;
	}

	public List getRateIndexNext(String id) throws Exception {
		List list = new ArrayList();
		String sql = "from RateIndex where parent.id ='"+id+"'";
		list = this.find(sql);
		return list;
	}

	public List getNames(String name) throws Exception {
		String sql = "from RateIndex where name='"+name+"'";
		List list = new ArrayList();
		list = this.find(sql);
		return list;
	}
}
