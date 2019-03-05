package com.sinocontact.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

public class PropertyUtils {
	private static final Logger logger = Logger.getLogger(PropertyUtils.class);
	
	private static String configFileName = "config.properties";
	private static Properties p = null;
	
	static
	{
		p = new Properties();
		try
        {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFileName);
            p.load(is);
            is.close();
        }
        catch (IOException e)
        {
        	logger.error("读取"+configFileName+"配置文件出错:", e);
        }
	}
	
	public static Properties load(String fileName)
    {
		Properties properties = new Properties();
        try
        {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            properties.load(is);
            is.close();
        }
        catch (IOException e)
        {
        	logger.error("读取"+fileName+"配置文件出错:", e);
        }
        return properties;
    }
	
	
	public static String getProperty(String key)
	{
		return p.getProperty(key);
	}
	public static String getProperty(String key,String defaultVal)
	{
		return p.getProperty(key,defaultVal);
	}
	

}
