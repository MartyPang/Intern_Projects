package com.sinocontact.common;

public class ConstCommon {
	//数据库连接默认配置文件名称
	public static final String DB_CONFIG_FILE = "db.properties";
	//用户信息保存session名称
	public static final String USERCONTEXT = "usercontext";
	//cookie失效时间（秒）[默认是30分钟]
	public static final int COOKIE_EXPIRE_SECONDS = 1800;
			
	//config 配置文件名
	public static final String CONFIG_PROPERTY_NAME = "config";	
		    
	//登录者 标记
	public static final String  LK_LOGIN_USERID = "lk_login_userid";
	//域名
	//public static final String BASE_PATH="http://mclarenmedia.cn";
	public static final String BASE_PATH="192.168.0.25:8088";

	//==================页面标识==================================
	//登录首页
	public static final String PAGE_USER_LOGIN ="login";
}
