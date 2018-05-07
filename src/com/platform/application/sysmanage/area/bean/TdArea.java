package com.platform.application.sysmanage.area.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.platform.application.sysmanage.area.TdAreaDto;

/**
 * 辖区实体类.
 */
@Entity
@Table(name = "TD_AREA")
@SuppressWarnings("serial")
public class TdArea implements java.io.Serializable {

	/**
	 * 辖区代码.
	 */
	private String areaId;

	/**
	 * 辖区名称.
	 */
	private String name;

	/**
	 * 上级辖区代码.
	 */
	private String upArea;

	/**
	 * 辖区级别.
	 */
	private String levels;

	/**
	 * 辖区说明.
	 */
	private String description;

	/**
	 * 预留字段.
	 */
	private String reserve;

	/**
	 * 获取辖区代码.
	 * @return  辖区代码
	 */
	@Id
	@Column(name = "AREAID", unique = true, nullable = false)
	public String getAreaId() {
		return areaId;
	}

	/**
	 * 设置辖区代码.
	 * @param areaId
	 */
	public void setAreaId(final String areaId) {
		this.areaId = areaId;
	}

	/**
	 * 获取辖区名称.
	 * @return 辖区名称
	 */
	@Column(name = "NAME", nullable = false)
	public String getName() {
		return name;
	}

	/**
	 * 设置辖区名称.
	 * @param name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * 获取上级辖区代码.
	 * @return 上级辖区代码
	 */
	@Column(name = "UPAREA")
	public String getUpArea() {
		return upArea;
	}

	/**
	 * 设置上级辖区代码.
	 * @param upArea
	 */
	public void setUpArea(final String upArea) {
		this.upArea = upArea;
	}

	/**
	 * 获取辖区级别.
	 * @return
	 */
	@Column(name = "LEVELS")
	public String getLevels() {
		return levels;
	}

	/**
	 * 设置辖区级别.
	 * @param levels
	 */
	public void setLevels(final String levels) {
		this.levels = levels;
	}

	/**
	 * 获取辖区说明.
	 * @return
	 */
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	/**
	 * 设置辖区说明.
	 * @param description
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * 预留字段
	 * @return 预留字段
	 */
	@Column(name = "RESERVE")
	public String getReserve() {
		return reserve;
	}

	/**
	 * 预留字段
	 */
	public void setReserve(final String reserve) {
		this.reserve = reserve;
	}

	@Override
	public String toString() {
		final StringBuilder buffer = new StringBuilder();
		buffer.append(getClass().getName()).append(" [");
		buffer.append("areaId").append("='").append(getAreaId()).append("' ");
		buffer.append("name").append("='").append(getName()).append("' ");
		buffer.append("upArea").append("='").append(getUpArea()).append("' ");
		buffer.append("levels").append("='").append(getLevels()).append("' ");
		buffer.append("description").append("='").append(getDescription()).append("' ");
		buffer.append("reserve").append("='").append(getReserve()).append("' ");
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * 转换交互对象.
	 *
	 * @return 系统数据字典交互对象
	 */
	public TdAreaDto convertDto() {
		TdAreaDto dto = new TdAreaDto();
		dto.setAreaId(areaId);
		dto.setName(this.name);
		dto.setUpArea(this.upArea);
		dto.setLevels(levels);
		dto.setDescription(description);
		dto.setReserve(this.reserve);
		return dto;
	}
}
