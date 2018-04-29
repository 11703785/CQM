package com.platform.application.sysmanage.datadic;

/**
 * 系统数据字典键值映射类.
 * Created by aigf on 2015/10/19.
 */
public class TdDictionaryDto implements Comparable<TdDictionaryDto> {
    /**
     * 数据字典键.
     */
    private String key;
    /**
     * 数据字典值.
     */
    private String value;

    /**
     * 构造函数.
     *
     * @param keyVal   数据字典键
     * @param valueVal 数据字典值
     */
    public TdDictionaryDto(final String keyVal, final String valueVal) {
        this.key = keyVal;
        this.value = valueVal;
    }

    /**
     * 获取数据字典键.
     *
     * @return 数据字典键
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置数据字典键.
     *
     * @param keyVal 数据字典键
     */
    public void setKey(final String keyVal) {
        this.key = keyVal;
    }

    /**
     * 获取数据字典值.
     *
     * @return 数据字典值
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置数据字典值.
     *
     * @param valueVal 数据字典值
     */
    public void setValue(final String valueVal) {
        this.value = valueVal;
    }

    @Override
    public int compareTo(final TdDictionaryDto o) {
        if (this.getKey() != null) {
            if (o.getKey() != null) {
                return this.getKey().compareTo(o.getKey());
            } else {
                return 1;
            }
        } else {
            if (o.getKey() != null) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
