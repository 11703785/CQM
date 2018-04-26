package com.platform.application.rateindex.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.application.rateindex.domain.RateIndex;
import com.platform.application.rateindex.domain.RateTempIndex;
import com.platform.application.rateindex.domain.RateTempIndexScore;
import com.platform.application.rateindex.domain.RateTemplate;
import com.platform.application.rateindex.service.RateIndexService;
import com.platform.application.rateindex.service.RateTempIndexScoreService;
import com.platform.application.rateindex.service.RateTempIndexService;
import com.platform.framework.core.dao.impl.GenericHibernateDao;

@Service
public class RateTempIndexServiceImpl extends GenericHibernateDao<RateTempIndex,String> implements RateTempIndexService {

	private RateIndexService rateIndexService;
	private RateTempIndexScoreService rateTempIndexScoreService;
	
	@Autowired
	public void setRateIndexService(RateIndexService rateIndexService) {
		this.rateIndexService = rateIndexService;
	}
	
	@Autowired
	public void setRateTempIndexScoreService(
			RateTempIndexScoreService rateTempIndexScoreService) {
		this.rateTempIndexScoreService = rateTempIndexScoreService;
	}


	public void txSaveRateTempIndex(RateTemplate temp, String strs) throws Exception {
		String[] str = strs.split(";");
		
		for(int i=0; i<str.length; i++){
			String[] a = str[i].split(",");
			String sqls = "select temps from RateTempIndex temps where temps.temp.id='"+temp.getId()+"' and temps.indexId='"+a[0]+"'";
			List lists = this.find(sqls);
			if(lists.size()>0){//编辑
				RateTempIndex temps = (RateTempIndex)lists.get(0);
				temps.setWeight(Double.valueOf(a[1]));
				
				this.update(temps);
			}else{//新增
				RateIndex index = (RateIndex)rateIndexService.load(a[0]);
				
				RateTempIndex tempIndex = new RateTempIndex();
				tempIndex.setId(null);
				tempIndex.setTemp(temp);
				tempIndex.setIndexId(index.getId());
				tempIndex.setIndexName(index.getName());
				tempIndex.setIndexLevels(index.getLevels());
				tempIndex.setIndexValues(index.getIndexValues());
				tempIndex.setIndexOrder(index.getOrderNum());
				tempIndex.setIndexvType(index.getIndexvtype());
				tempIndex.setIndexOrder(index.getOrderNum());
				if(index.getParent()!=null){
					tempIndex.setParentIndexId(index.getParent().getId());
				}
				tempIndex.setWeight(Double.valueOf(a[1]));
				
				this.save(tempIndex);
			}
		}
	}

	public RateTempIndex queryRateTempIndex(String ids) {
		String[] str = ids.split(";");
		String tempid = str[0];
		String indexId = str[1];
		String sql = "from RateTempIndex where indexId='"+indexId+"' and  temp.id='"+tempid+"'" ;
		List<RateTempIndex> list  = this.find(sql);
		RateTempIndex rateTempIndex = new RateTempIndex();
		 if(list.size() > 0){
			rateTempIndex = list.get(0);
		 }
		return rateTempIndex;
	}

	public void txDeleteRateTempIndex(String tempid, String indexid) throws Exception {
		String sql = "select temps from RateTempIndex temps where temps.temp.id='"+tempid+"' and temps.indexId='"+indexid+"'";
		List list = this.find(sql);
		if(list.size() > 0){
			RateTempIndex temp = (RateTempIndex)list.get(0);
			
			String sqls = "select temps from RateTempIndex temps where temps.temp.id='"+tempid+"' and temps.parentIndexId='"+temp.getIndexId()+"'";
			List lists = this.find(sqls);
			for(int i=0; i<lists.size(); i++){
				RateTempIndex temps = (RateTempIndex)lists.get(i);
				String csql = "select c from RateTempIndexScore c where c.temp.id='"+tempid+"' and c.indexId='"+temps.getIndexId()+"'";
				List clist = this.find(csql);
				for(int j=0; j<clist.size(); j++){
					RateTempIndexScore c = (RateTempIndexScore)clist.get(j);
					
					rateTempIndexScoreService.delete(c);
				}
				this.delete(temps);
			}
			
			String csql = "select c from RateTempIndexScore c where c.temp.id='"+tempid+"' and c.indexId='"+temp.getIndexId()+"'";
			List clist = this.find(csql);
			for(int j=0; j<clist.size(); j++){
				RateTempIndexScore c = (RateTempIndexScore)clist.get(j);
				
				rateTempIndexScoreService.delete(c);
			}
			this.delete(temp);
		}
	}
}
