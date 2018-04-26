package com.platform.application.common.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import com.platform.application.common.util.Constants;

/**
 * 字典实体
 */
@Entity
@Table(name = "sys_dictionary")
public class Dictionary implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final String cate = "01";
	public static final String value = "05";
	
	private String id = null;			    //主键
	private String code = null;			    //字典编码
	private String name = null;           	//字典值
	private String memo = null;           	//字典说明
	private Dictionary parent = null;       //父字典
	private String treeCode = null;         //ID层级字串儿，用/分隔
	private String status = Constants.STATUS_OPEN;      //状态
	//private String dicType = cate;          //字典类型  01 cate|字典分类    05 value|字典值
	private int orderNum = 0;              //序号
	private String isleaf = Constants.LEAF_1;           //是否叶子节点    0 非叶子节点   1 叶子节点
	
	private DicType dicType;				//字典类型

	@Id
	@GenericGenerator(name="uuidGenerator",strategy="uuid2")
	@GeneratedValue(generator="uuidGenerator")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="parentId")
	public Dictionary getParent() {
		return parent;
	}
	public void setParent(Dictionary parent) {
		this.parent = parent;
	}
	
	public String getTreeCode() {
		return treeCode;
	}
	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	
	public String getIsleaf() {
		return isleaf;
	}
	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}
	
	@ManyToOne(fetch= FetchType.LAZY, targetEntity = DicType.class)
	@JoinColumn(name="typeid")
	public DicType getDicType() {
		return dicType;
	}
	public void setDicType(DicType dicType) {
		this.dicType = dicType;
	}

}
