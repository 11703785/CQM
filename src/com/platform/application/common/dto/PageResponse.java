package com.platform.application.common.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 集合分页传输对象.
 *
 * @param <T> 集合类型
 */
@SuppressWarnings("serial")
public class PageResponse<T> implements java.io.Serializable {
    /**
     * 状态.
     */
    private boolean status;
    /**
     * 错误原因.
     */
    private String error;
    /**
     * 总数.
     */
    private int total;
    /**
     * 当前页.
     */
    private int current;
    /**
     * 页数量.
     */
    private int pageSize;
    /**
     * 结果列表.
     */
    private List<T> rows;
    /**
     * 错误描述.
     */
    private Map<String, String> errorMap = new HashMap<String, String>();

    /**
     * 默认构造函数.
     */
    public PageResponse() {

    }

    /**
     * 构造函数.
     *
     * @param statusVal 状态
     * @param errorVal  错误原因
     */
    public PageResponse(final boolean statusVal, final String errorVal) {
        this.status = statusVal;
        this.error = errorVal;
    }

    /**
     * 构造函数.
     *
     * @param statusVal   状态
     * @param count       总数
     * @param currentVal  当前页
     * @param pageSizeVal 页数量
     * @param rowsVal     结果列表
     */
    public PageResponse(final boolean statusVal,
                        final int count,
                        final int currentVal,
                        final int pageSizeVal,
                        final List<T> rowsVal) {
        this.status = statusVal;
        this.total = count;
        this.current = currentVal;
        this.pageSize = pageSizeVal;
        this.rows = rowsVal;
    }

    /**
     * 构造函数.
     *
     * @param statusVal   状态
     * @param errorVal    错误原因
     * @param count       总数
     * @param currentVal  当前页
     * @param pageSizeVal 页数量
     * @param rowsVal     结果列表
     */
    public PageResponse(final boolean statusVal,
                        final String errorVal,
                        final int count,
                        final int currentVal,
                        final int pageSizeVal,
                        final List<T> rowsVal) {
        this.status = statusVal;
        this.error = errorVal;
        this.total = count;
        this.current = currentVal;
        this.pageSize = pageSizeVal;
        this.rows = rowsVal;
    }

    /**
     * 获取状态.
     *
     * @return 状态
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * 设置状态.
     *
     * @param statusVal 状态
     */
    public void setStatus(final boolean statusVal) {
        this.status = statusVal;
    }

    /**
     * 获取错误原因.
     *
     * @return 错误原因
     */
    public String getError() {
        return error;
    }

    /**
     * 设置错误原因.
     *
     * @param errorVal 错误原因
     */
    public void setError(final String errorVal) {
        this.error = errorVal;
    }

    /**
     * 获取总数.
     *
     * @return 总数
     */
    public int getTotal() {
        return total;
    }

    /**
     * 设置总数.
     *
     * @param totalVal 总数
     */
    public void setTotal(final int totalVal) {
        this.total = totalVal;
    }

    /**
     * 获取当前页.
     *
     * @return 当前页
     */
    public int getCurrent() {
        return current;
    }

    /**
     * 设置当前页.
     *
     * @param currentVal 当前页
     */
    public void setCurrent(final int currentVal) {
        this.current = currentVal;
    }

    /**
     * 获取页数量.
     *
     * @return 页数量
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置页数量.
     *
     * @param pageSizeVal 页数量
     */
    public void setPageSize(final int pageSizeVal) {
        this.pageSize = pageSizeVal;
    }

    /**
     * 获取结果列表.
     *
     * @return 结果列表
     */
    public List<T> getRows() {
        return rows;
    }

    /**
     * 设置结果列表.
     *
     * @param rowsVal 结果列表
     */
    public void setRows(final List<T> rowsVal) {
        this.rows = rowsVal;
    }

    /**
     * 获取错误描述.
     *
     * @return 错误描述
     */
    public Map<String, String> getErrorMap() {
        return errorMap;
    }

    /**
     * 设置错误描述.
     *
     * @param errorMapVal 错误描述
     */
    public void setErrorMap(final Map<String, String> errorMapVal) {
        this.errorMap = errorMapVal;
    }

}
