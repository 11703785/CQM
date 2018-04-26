/************************************************************

  Copyright (C), 1996-2008, resoft Tech. Co., Ltd.
  FileName: CollectionUtil.java
  Author: hubo       
  Version: 1.0     
  Date:2008-7-15 下午01:27:17
  Description: 集合工具类    

  Function List:   
    1. -------

  History:         
      <author>    <time>   <version >   <desc>


 ***********************************************************/

package com.platform.framework.common.util;

import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;

/**
 * @since 2008-7-15
 * @version 1.0
 */
public class CollectionUtil extends CollectionUtils {
	/**
	 * 判断一个集合是否为NULL
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isNull(Collection collection) {
		return collection == null ? true : false;
	}

	/**
	 * 判断一个集合是否不为NULL
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isNotNull(Collection collection) {
		return !isNull(collection);
	}

	/**
	 * 判断一个集合是否为空
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(Collection collection) {
		if (isNull(collection)) {
			return true;
		}
		if (collection.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断一个集合是否不为空
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isNotEmpty(Collection collection) {
		return !isEmpty(collection);
	}
}
