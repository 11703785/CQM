package com.platform.application.sysmanage.service;

import java.util.List;

import org.hibernate.Session;

import com.platform.application.sysmanage.domain.Notice;
import com.platform.application.sysmanage.domain.Person;
import com.platform.framework.core.dao.GenericDao;
import com.platform.framework.core.page.Page;

public interface NoticeService extends GenericDao<Notice,String> {
    /**
     * 公告信息JSON
     * @param no
     * @param pageObj
     * @return
     * @throws Exception 
     */
	public String getNoticesJSON(Notice no, Person person,Page pageObj) throws Exception;
   /**
    * 新建公文信息
    * @param notice
    * @param person
    * @param ip
    */
	public void txCreateNotice(Notice notice,String deptId, String personId)throws Exception;
	/**
	 * 跟新公文信息
	 * @param notice
	 * @param person
	 * @param ip
	 */
    public void txUpdateNotice(Notice notice, String deptId, String personId)throws Exception;
	/**
	 * 删除信息
	 * @param notice
	 */
    public void txDeleteNotice(Notice notice);
    /**
     * 查询最近的八条数据
     * */
    public List<Object[]> selNoticeData()throws Exception;
}
