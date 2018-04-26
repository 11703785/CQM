package com.platform.application.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

	private static Properties prop = new Properties();
	
	// 读取配置文件
	static{
		InputStream is = PropertyUtil.class.getClassLoader().getResourceAsStream("config/config.properties");
		try {
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//根据key读配置文件
	public static String getPropertyByKey(String key) {
		return prop.getProperty(key);
	}

}