package com.platform.application.sysmanage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.platform.application.sysmanage.domain.Notice;
import com.platform.application.sysmanage.domain.Person;
import com.platform.application.sysmanage.service.NoticeService;
import com.platform.framework.common.util.JsonUtil;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.dao.impl.GenericHibernateDao;
import com.platform.framework.core.page.Page;

@Service
public class NoticeServiceImpl extends GenericHibernateDao<Notice,String> implements NoticeService {

	public String getNoticesJSON(Notice no,Person person, Page page) throws Exception  {
		List para = new ArrayList();
		List nolist = this.getNotices(no,person,para,page);
		if(nolist.size()>0){
			for(Object obj:nolist){
				Object[] objarr=(Object[]) obj;
				Notice notice=(Notice) objarr[0];
				HashMap<String, String> taskmap=new HashMap<String, String>();
				taskmap.put("title", notice.getTitle());
				taskmap.put("contents", notice.getContents());
				taskmap.put("createTime", notice.getCreateTime().toString().substring(0, 10));
				taskmap.put("loginName", objarr[1].toString());
				taskmap.put("deptId", notice.getDeptId());
				taskmap.put("id", notice.getId());
				taskmap.put("personId", notice.getPersonId());
				para.add(taskmap);
			}
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("totalProperty", "recordsFiltered," + page.getTotalCount());
		map.put("root", "data");
		return JsonUtil.fromMapLists(para,map);
	}

	private List getNotices(Notice no,Person person,List notice, Page page) {
		List paras = new ArrayList(3);
		StringBuffer sb = new StringBuffer();
		sb.append("select notice,us.loginName from Notice as notice, UserAccount as us ");
		sb.append(" where us.userId='"+person.getUserAccount().getUserId()+"'");
		sb.append(" and notice.deptId='"+person.getDepartment().getDeptId()+"'");
		if(StringUtil.isNotBlank(no.getTitle())){
			sb.append(" and notice.title like '%"+no.getTitle()+"%'");
		}
		return find(sb.toString(), paras, page); 
	}

	public void txCreateNotice(Notice notice, String personId, String deptId)throws Exception {
		notice.setId(null);
		notice.setPersonId(personId);
		notice.setDeptId(deptId);
		notice.setCreateTime(new Date());
		verifyNotice(notice);//重复性校验
		this.save(notice);
	}
	public void verifyNotice(Notice notice) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("from Notice n where n.title=?");
		List param = new ArrayList(2);
		param.add(notice.getTitle());
		if(!StringUtil.isEmpty(notice.getId())){
			sb.append(" and n.id != ?");
			param.add(notice.getId());
		}
			List result = this.find(sb.toString(), param);
			if (result.size() > 0) {
				throw new Exception("存在重名的标题！");
			}
	}
	public void txUpdateNotice(Notice notice, String deptId, String personId)throws Exception {
		Notice tmpNotice = (Notice)this.load(notice.getId());
		//String name = tmpRole.getRoleName();
		verifyNotice(notice);
		tmpNotice.setCreateTime(new Date());
		tmpNotice.setTitle(notice.getTitle());
		tmpNotice.setContents(notice.getContents());
		tmpNotice.setPersonId(personId);
		tmpNotice.setDeptId(deptId);
		this.update(tmpNotice);
	}

	public void txDeleteNotice(Notice notice) {
		Notice bnotice = (Notice)this.load(notice.getId());
		this.delete(bnotice);
	}
	public List<Object[]> selNoticeData() throws Exception{
		List<Notice> nList=this.find("from Notice order by createTime desc");
		List<Object[]> list=new ArrayList<Object[]>();
		if(nList.size()>0){
			for(int i=0;i<nList.size();i++){
				if(i>7){
					break;
				}
				Notice notice=nList.get(i);
				Object[] no={notice.getId(),notice.getTitle(),notice.getCreateTime().toString().substring(0, 10)};
				list.add(no);
			}
		}
		 return list;  
	}
}
