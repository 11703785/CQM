package com.platform.application.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 结果响应传输公共对象.
 *
 * @param <T> 结果对象
 */
@JsonInclude(Include.NON_NULL)
public class ResultResponse<T> {
    /**
     * 结果状态.
     */
    private boolean status;
    /**
     * 错误原因.
     */
    private String error;
    /**
     * 结果对象.
     */
    private T result;
    /**
     * 结果列表.
     */
    private List<T> rows;
    /**
     * 错误描述.
     */
    private Map<String, String> errorMap;
    /**
     * 结果状态码.
     */
    private String statusCode;
    /**
     * 剩余查询量
     */
    private Integer margin;
    /**
     * 超过阈值百分比
     */
    private double overlimit;
    /**
     * 已用查询量
     */
    private Integer used;
    /**
     * 弹窗状态码
     */
    private Integer staNum;
    /**
     * 请求队列数量
     */
    private Integer accNum;
    /**
     * 系统查询频次
     */
    private Integer queryFrequency;
    /**
     * 返回状态
     */
    private Integer staNumb;

    /**
     * 默认构造函数.
     */
    public ResultResponse() {
    }

    /**
     * 构造函数.
     *
     * @param statusVal 状态
     */
    public ResultResponse(final boolean statusVal) {
        this.status = statusVal;
    }

    /**
     * 构造函数.
     *
     * @param statusVal 状态
     * @param errorVal  错误原因
     */
    public ResultResponse(final boolean statusVal, final String errorVal) {
        this.status = statusVal;
        this.error = errorVal;
    }

    /**
     * 构造函数.
     *
     * @param statusVal 状态
     * @param errorVal  错误原因
     * @param fes       错误列表
     */
    public ResultResponse(final boolean statusVal, final String errorVal,
                          final List<ObjectError> fes) {
        this.status = statusVal;
        this.error = errorVal;
        this.add(fes);
    }

    /**
     * 构造函数.
     *
     * @param statusVal 状态
     * @param resultVal 结果
     */
    public ResultResponse(final boolean statusVal, final T resultVal) {
        this.status = statusVal;
        this.result = resultVal;
    }

    public ResultResponse(final boolean statusVal, final List<T> rows) {
        this.status = statusVal;
        this.rows = rows;
    }

    /**
     * 获取请求队列数量
     *
     * @return 请求队列数量
     */
    public Integer getAccNum() {
        return accNum;
    }

    /**
     * 设置请求队列数量
     *
     * @param accNum 请求队列数量
     */
    public void setAccNum(Integer accNum) {
        this.accNum = accNum;
    }

    /**
     * 获取查询频次
     *
     * @return 请求查询频次
     */
    public Integer getQueryFrequency() {
        return queryFrequency;
    }

    /**
     * 设置查询频次
     *
     * @param queryFrequency 查询频次
     */
    public void setQueryFrequency(Integer queryFrequency) {
        this.queryFrequency = queryFrequency;
    }

    /**
     * 获取返回状态
     *
     * @return 请求返回状态
     */
    public Integer getStaNumb() {
        return staNumb;
    }

    /**
     * 设置返回状态
     *
     * @param staNumb 请求返回状态
     */
    public void setStaNumb(Integer staNumb) {
        this.staNumb = staNumb;
    }

    /**
     * 获取弹窗状态码
     *
     * @return 弹窗状态码
     */
    public Integer getStaNum() {
        return staNum;
    }

    /**
     * 设置弹窗状态码
     *
     * @param margin 弹窗状态码
     */
    public void setStaNum(Integer i) {
        this.staNum = i;
    }

    /**
     * 获取剩余查询量
     *
     * @return 剩余查询量
     */
    public Integer getMargin() {
        return margin;
    }

    /**
     * 设置剩余查询量
     *
     * @param margin 剩余查询量
     */
    public void setMargin(Integer margin) {
        this.margin = margin;
    }

    /**
     * 获取超过阈值百分比
     *
     * @return 超过阈值百分比
     */
    public double getOverlimit() {
        return overlimit;
    }

    /**
     * 设置超过阈值百分比
     *
     * @param d 超过阈值百分比
     */
    public void setOverlimit(double d) {
        this.overlimit = d;
    }

    /**
     * 获取已用查询量
     *
     * @return 已用查询量
     */
    public Integer getUsed() {
        return used;
    }

    /**
     * 设置已用查询量
     *
     * @param used 已用查询量
     */
    public void setUsed(Integer used) {
        this.used = used;
    }

    /**
     * 获取结果状态.
     *
     * @return 结果状态
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * 设置结果状态.
     *
     * @param statusVal 结果状态
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

    /**
     * 获取结果对象.
     *
     * @return 结果对象
     */
    public T getResult() {
        return result;
    }

    /**
     * 设置结果对象.
     *
     * @param resultVal 结果对象
     */
    public void setResult(final T resultVal) {
        this.result = resultVal;
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
     * 获取结果状态码.
     *
     * @return 结果状态码
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * 设置结果状态码.
     *
     * @param statusCodeVal 结果状态码
     */
    public void setStatusCode(final String statusCodeVal) {
        this.statusCode = statusCodeVal;
    }

    /**
     * 添加错误描述.
     *
     * @param fes 错误列表
     */
    public void add(final List<ObjectError> fes) {
        if (errorMap == null) {
            errorMap = new HashMap<String, String>();
        }
        for (final ObjectError fe : fes) {
            String key = fe.getCode();
            if (fe instanceof FieldError) {
                key = ((FieldError) fe).getField();
            }
            final String message = errorMap.get(key);
            if (StringUtils.isBlank(message)) {
                errorMap.put(key, fe.getDefaultMessage());
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append(getClass().getName()).append(" [");
        buffer.append("status").append("='").append(isStatus()).append("' ");
        buffer.append("error").append("='").append(getError()).append("' ");
        buffer.append("result").append("='").append(getResult()).append("' ");
        return buffer.toString();
    }

}
