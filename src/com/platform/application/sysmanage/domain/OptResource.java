package com.platform.application.sysmanage.domain;

import java.io.Serializable;
import java.util.Comparator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.platform.application.common.util.Constants;

/**
 * 操作资源实体
 */
@SuppressWarnings("rawtypes")
@Entity
@Table(name = "sys_optresource")
public class OptResource implements Comparator,Serializable {

	private static final long serialVersionUID = 1L;
	public static final String dir = "01";
	public static final String menu = "05";
	public static final String operation = "10";
	
	private String resId;					//资源ID
	private String resName;				    //资源名称
	private String resCode;                 //资源代码
	private String resType = "01";          //资源类型01 dir|目录    05 menu|菜单（叶子） 10 operation|操作（叶子）
	private int resOrder = 0;               //资源序号
	private String actions = "";            //资源调用的Action名称
	private String resUrl = "";             //menu菜单对应的链接
	private OptResource parent;			    //父资源
	private String pictureclass;			//图标样式

	@Id
	@GenericGenerator(name="uuidGenerator",strategy="uuid2")
	@GeneratedValue(generator="uuidGenerator")
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	@Transient
	public String getResTypeStr(){
		if(this.resType.equals("01")){
			return "目录";
		}else if(this.resType.equals("05")){
			return "菜单";
		}else{
			return "操作";
		}
	}

	public int getResOrder() {
		return resOrder;
	}
	public void setResOrder(int resOrder) {
		this.resOrder = resOrder;
	}
	
	public String getActions() {
		return actions;
	}
	public void setActions(String actions) {
		this.actions = actions;
	}
	
	public String getResUrl() {
		return resUrl;
	}
	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parentId")
	public OptResource getParent() {
		return parent;
	}
	public void setParent(OptResource parent) {
		this.parent = parent;
	}

	public int compare(Object o1, Object o2) {
		OptResource res1 = (OptResource)o1;
		OptResource res2 = (OptResource)o2;
//		boolean flag = res1.getResOrder()>=res2.getResOrder();
//		if(flag) return 1;
//		return 0;
		return res1.getResOrder() == res2.getResOrder() ? 0 :(res1.getResOrder() > res2.getResOrder() ? 1 : -1);
	}

	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}else{
			return this.resId.equals(((OptResource)obj).getResId());
		}
	}
	public String getPictureclass() {
		return pictureclass;
	}
	public void setPictureclass(String pictureclass) {
		this.pictureclass = pictureclass;
	}

}