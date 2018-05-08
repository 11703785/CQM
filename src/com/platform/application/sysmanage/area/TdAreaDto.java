package com.platform.application.sysmanage.area;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.platform.application.common.dto.AbstractDto;
import com.platform.application.sysmanage.area.bean.TdArea;

@SuppressWarnings("serial")
public class TdAreaDto extends AbstractDto implements Serializable {
	/**
	 * 辖区代码.
	 */
	@NotBlank(message = "辖区代码不能为空")
	@Size(max = 50, message = "辖区代码长度超出")
	private String areaId;

	/**
	 * 辖区名称.
	 */
	@NotBlank(message = "辖区名称不能为空")
	@Size(max = 50, message = "辖区名称长度超出")
	private String name;

	/**
	 * 上级辖区代码.
	 */
	@NotBlank(message = "上级辖区代码不能为空")
	@Size(max = 50, message = "上级辖区代码长度超出")
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
	public String getName() {
		return name;
	}

	/**
	 * 设置辖区名称.
	 * @param name
	 */
	public void setName(final String name) {
		setOperName(name + "[" + getAreaId() + "]");
		this.name = name;
	}

	/**
	 * 获取上级辖区代码.
	 * @return 上级辖区代码
	 */
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
	 * 转换实体对象.
	 *
	 * @return 系统数据字典实体类
	 */
	public TdArea convertEntity() {
		TdArea entity = new TdArea();
		entity.setAreaId(this.areaId);
		entity.setName(this.name);
		entity.setUpArea(this.upArea);
		entity.setLevels(levels);
		entity.setDescription(description);
		entity.setReserve(this.reserve);
		return entity;
	}
}
