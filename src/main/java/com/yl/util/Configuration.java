package com.yl.util;

import java.io.InputStream;
import java.util.Properties;


public class Configuration {
	
	
	private Properties prop ;
	
	private Configuration(){
		InputStream is;
		try {
			is = Configuration.class.getClassLoader().getResourceAsStream("datasource.properties");
			prop.load(is);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private static class Singleton{
		private static Configuration cfg = new Configuration();
	}
	
	public static Configuration getInstace(){
		return Singleton.cfg;
	}
	
	public String getVal(String key){
		return prop.getProperty(key);
	}
}
