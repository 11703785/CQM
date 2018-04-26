package com.platform.framework.core.page;

import java.io.Serializable;
import java.util.List;

/**
 * 说明:全局翻页类
 * 
 */
public class Page implements Serializable {

	private static final long serialVersionUID = 474686333071715399L;
	public static final int DEFAULT_PAGE_SIZE = 10;  // 默认页数

	private int pageSize = DEFAULT_PAGE_SIZE;
	private int pageIndex = 1;        //当前页
	private long totalCount = 1;      //总行数
	private int pageCount = 1;        //总页数

	private String dir = "DESC";      //ASC或DESC排序方法
	private String sort = null;       //排序字段名
	private List result = null;       //查询结果集

	public Page() {}

	/**
	 * @param pageIndex
	 * @param pageSize
	 */
	public Page(int pageIndex, int pageSize) {
		if (pageIndex < 1) pageIndex = 1;
		if (pageSize < 1) pageSize = 1;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}

	public Page(int pageIndex) {
		this(pageIndex, DEFAULT_PAGE_SIZE);
	}

	public int getPageIndex() {
		return this.pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageCount() {
		return this.pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}

	public List getResult() {
		return result;
	}
	public void setResult(List result) {
		this.result = result;
	}

}
