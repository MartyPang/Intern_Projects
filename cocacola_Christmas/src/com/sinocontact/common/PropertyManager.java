package com.sinocontact.common;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/*
 * 类名：PropertyManager
 * 功能：加载配置信息
 * */
public class PropertyManager {

	private static final Log logger = LogFactory.getLog(PropertyManager.class);
	//是否已经初始化过 false表示未初始化过 ；true表示已经初始化。
	private static boolean m_bInited = false;   
    //配置文件中的配置项目。
    protected static Properties m_vProperties = new Properties();
    static 
    {
    	//加载配置文件
    	loadPropertyFile("config");
    }
    //加载配置文件
    protected static void loadPropertyFile(String propertyFile)
    {
        //如果已经初始化过了，则不再进行初始化。
        if ( m_bInited )
            return;
        ResourceBundle bundle =null;
      //把配置信息转换成Properties
        try{
        	//读取配置文件
        	bundle = ResourceBundle.getBundle( propertyFile );       
	        Enumeration vEnumeration = bundle.getKeys();
	        Object oKey = null;
	        Object oValue = null;
	        while( vEnumeration.hasMoreElements() )
	        {
	            oKey = vEnumeration.nextElement();
	            oValue = bundle.getString( oKey.toString() );
	            m_vProperties.put( oKey, oValue );
	        }
	        bundle = null;
	        m_bInited = true;
        }catch(Exception e){        	
        	bundle = null;
	        m_bInited = false;
	        logger.error("PropertyManager的loadPropertyFile函数出现异常：",e);
        }        
        
    }
    public static Properties loadProperty(String propertyFile){
    	Properties property=new Properties();
    	ResourceBundle bundle =null;
        //把配置信息转换成Properties
          try{
          	//读取配置文件
          	bundle = ResourceBundle.getBundle( propertyFile );       
  	        Enumeration vEnumeration = bundle.getKeys();
  	        Object oKey = null;
  	        Object oValue = null;
  	        while( vEnumeration.hasMoreElements() )
  	        {
  	          oKey = vEnumeration.nextElement();
  	          oValue = bundle.getString( oKey.toString() );
  	          property.put( oKey, oValue );
  	        }
          }catch(Exception e){        	
  	        logger.error("PropertyManager的loadPropertyFile函数出现异常：",e);
          }  
    	return property;
    }
    //根据key获取配置信息的内容
    public static String getProperty( String key )
    {
        return m_vProperties.getProperty( key );
    }
}
