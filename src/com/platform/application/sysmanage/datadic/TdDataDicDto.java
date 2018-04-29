package com.platform.application.sysmanage.datadic;

import com.platform.application.common.dto.AbstractDto;
import com.platform.application.sysmanage.datadic.bean.TdDataDic;

/**
 * 系统数据字典传输类.
 *
 * @see TdDataDic
 */
@SuppressWarnings("serial")
public class TdDataDicDto extends AbstractDto implements Comparable<TdDataDicDto> {

	/**
	 * 数据字典编号.
	 */
	private Long id;

	/**
	 * 数据字典代码.
	 */
	private String code;

	/**
	 * 数据字典名称.
	 */
	private String name;

	/**
	 * 数据字典大类.
	 */
	private String type;

	/**
	 * 数据字典小类.
	 */
	private String subdivision;

	/**
	 * 数据字典描述.
	 */
	private String describe;

	/**
	 * 数据字典代码预留字段.
	 */
	private String reserve;

	/**
	 * 获取数据字典编号.
	 *
	 * @return 数据字典编号
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * 设置数据字典编号.
	 *
	 * @param idVal 数据字典编号
	 */
	public void setId(final Long idVal) {
		this.id = idVal;
	}

	/**
	 * 获取数据字典代码.
	 *
	 * @return 数据字典代码
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * 设置数据字典代码.
	 *
	 * @param codeVal 数据字典代码
	 */
	public void setCode(final String codeVal) {
		this.code = codeVal;
	}

	/**
	 * 获取数据字典名称.
	 *
	 * @return 数据字典名称
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置数据字典名称.
	 *
	 * @param nameVal 数据字典名称
	 */
	public void setName(final String nameVal) {
		this.name = nameVal;
	}

	/**
	 * 获取数据字典大类.
	 *
	 * @return 数据字典大类
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * 设置数据字典大类.
	 *
	 * @param typeVal 数据字典大类
	 */
	public void setType(final String typeVal) {
		this.type = typeVal;
	}

	/**
	 * 获取数据字典小类.
	 *
	 * @return 数据字典小类
	 */
	public String getSubdivision() {
		return this.subdivision;
	}

	/**
	 * 设置数据字典小类.
	 *
	 * @param subdivisionVal 数据字典小类
	 */
	public void setSubdivision(final String subdivisionVal) {
		this.subdivision = subdivisionVal;
	}

	/**
	 * 获取数据字典描述.
	 *
	 * @return 数据字典描述
	 */
	public String getDescribe() {
		return this.describe;
	}

	/**
	 * 设置数据字典描述.
	 *
	 * @param describeVal 数据字典描述
	 */
	public void setDescribe(final String describeVal) {
		this.describe = describeVal;
	}

	/**
	 * 获取数据字典预留字段.
	 *
	 * @return 数据字典预留字段
	 */
	public String getReserve() {
		return this.reserve;
	}

	/**
	 * 设置数据字典预留字段.
	 *
	 * @param reserveVal 数据字典预留字段
	 */
	public void setReserve(final String reserveVal) {
		this.reserve = reserveVal;
	}

	@Override
	public String toString() {
		final StringBuilder buffer = new StringBuilder();
		buffer.append(getClass().getName()).append(" [");
		buffer.append("id").append("='").append(getId()).append("' ");
		buffer.append("code").append("='").append(getCode()).append("' ");
		buffer.append("name").append("='").append(getName()).append("' ");
		buffer.append("type").append("='").append(getType()).append("' ");
		buffer.append("subdivision").append("='").append(getSubdivision())
		.append("' ");
		buffer.append("describe").append("='").append(getDescribe())
		.append("' ");
		buffer.append("reserve").append("='").append(getReserve()).append("' ");
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * 转换实体对象.
	 *
	 * @return 系统数据字典实体类
	 */
	public TdDataDic convertEntity() {
		TdDataDic entity = new TdDataDic();
		entity.setId(this.id);
		entity.setCode(this.code);
		entity.setName(this.name);
		entity.setType(this.type);
		entity.setSubdivision(this.subdivision);
		entity.setDescribe(this.describe);
		entity.setReserve(this.reserve);
		return entity;
	}

	@Override
	public int compareTo(final TdDataDicDto o) {
		if (this.getCode() != null) {
			if (o.getCode() != null) {
				return this.getCode().compareTo(o.getCode());
			} else {
				return 1;
			}
		} else {
			if (o.getCode() != null) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}
