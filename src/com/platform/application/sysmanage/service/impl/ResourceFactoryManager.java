package com.platform.application.sysmanage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.application.sysmanage.domain.OptResource;
import com.platform.application.sysmanage.service.OptResourceService;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.dao.impl.GenericHibernateDao;

@Service
public class ResourceFactoryManager extends GenericHibernateDao<OptResource,String> {
	
	public static Map allResMap = new HashMap();     //所有资源Map
	public static Map allActionMap = new HashMap();  //所有ActionMap
	private OptResourceService optResourceService = null;

	@Autowired
	public void setOptResourceService(OptResourceService optResourceService) {
		this.optResourceService = optResourceService;
	}

	@PostConstruct
	public void initResourceCache(){
		try {
			System.out.println("Begin loading resource...");
			// 所有资源对象处理
			List allRes = optResourceService.loadAll();
			putResource(allRes);
			System.out.println("End load resource... success!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将所有资源通过code放入map中
	 */
	public static void putResource(List resList) {
		OptResource res = null;
		String[] arr = null;
		for (int i = 0; i < resList.size(); i++) {
			res = (OptResource) resList.get(i);
			allResMap.put(res.getResCode(), res);
			if (StringUtil.isNotEmpty(res.getActions())){
				arr = res.getActions().split(",");
				for (int j = 0; j < arr.length; j++) {
					allActionMap.put(arr[j], arr[j]);
				}
			}
		}
	}

}
