package com.sinocontact.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 类名：CookieManager
 * 功能：cookie管理
 */
public class CookieManager {
	
	private static final Log logger = LogFactory.getLog(CookieManager.class);

	
	/**
	 * 功能：当前访问的用户的指定cookie中的值
	 * 参数：name 是cookie的name
	 * 返回值：对应于name的value
	 */
	private static String getCookieValue(HttpServletRequest req,String name){
		String value = null;
		try{
			Cookie[] cookies = req.getCookies();
			if(cookies == null)
				return value;
			
			//遍历方式查找Cookies中是否存在 name
			for(Cookie c : cookies){
				
				if(c.getName().equals(name) == true){//在cookies中找到name的cookie
					value = c.getValue();
					break;
				}
			}
		}catch(Exception e){
			value = null;
			logger.error("CookieManager的getCookieValue函数出现异常：",e);
		}
		return value;
	}

	/**
	 * 获取Cookie中存放的标识所对应的值，也就是上面setCookieId（）所生成的唯一ID
	 * @param cookieKey	Cookie的存放标识
	 * @param req		request对象
	 * @return
	 */
	public static String getCookieId(String cookieKey,HttpServletRequest req){
		String cookieId  = "";
		cookieId = getCookieValue(req,cookieKey);
		return cookieId;
	}
	
	
	//对字符串值进行urlencode
	public static String URLencode(String value){
		String encodeValue = value;
		try{
			if (StringUtils.isNotEmpty(value)) {
				encodeValue = URLEncoder.encode(value, "UTF-8"); 
			}
		}catch(Exception e){
			logger.error("CookieManager的URLencode函数出现异常：",e);
		}
		return encodeValue;
	}
	
	//对字符串值进行urldecode
	public static String URLdecode(String value){
		String decodeValue = value;
		try {
			if (StringUtils.isNotEmpty(value)) {
				decodeValue = URLDecoder.decode(value, "UTF-8");
			}
		} catch (Exception e) {
			logger.error("CookieManager的URLdecode函数出现异常：",e);
		}  
		return decodeValue;
	}
	
	/**
	 * 从cookie里取值
	 */
	public String getCookieValues(HttpServletRequest req,String key){
		 String strCookie="";
		 Cookie[] cookies = req.getCookies();   
		 try{  
	          if (cookies != null && cookies.length > 0) { //如果没有设置过Cookie会返回null  
	        		for (Cookie cookie : cookies) {
	        			 if(cookie.getName().equals(key)){
	        				 strCookie = URLDecoder.decode(cookie.getValue(),"UTF-8");
	        			 }
	        	    } 
	          }
	      }catch(Exception ex)   
	      {   
	           System.out.println("Cookies里取值发生异常！");   
	      }
		return strCookie; 
	}
	
	/**
	 * 删除cookie
	 */
	public void clearCookie(HttpServletRequest req,HttpServletResponse reb,String key){
		 Cookie[] cookies = req.getCookies();   
		 try {   
			 		for (Cookie cookiee : cookies) {
		        	    if(cookiee.getName().equals(key)){
		        	    	Cookie cookie = new Cookie(cookiee.getName(), null);
		        	    	cookie.setMaxAge(0); 
		        	    	cookie.setPath("/");
		        	    	reb.addCookie(cookie);  
	        			}
		           }   
	      }catch(Exception ex)   
	      {   
	           System.out.println("清空Cookies发生异常！");   
	      }    
	}
	
	/**
	 * 保存cookie
	 * @param key
	 * @param value
	 */
	public void saveCookie(HttpServletResponse reb,String key,String value){
		//新建cookie
		try {
			//设置编码类型
			value = URLEncoder.encode(value,"UTF-8");
			Cookie cookie =new Cookie(key, value);
			//设置cookie的作用域，/为整个web
			cookie.setPath("/");
			reb.addCookie(cookie); 
		} catch (Exception e) {
			logger.error("CookieManager 类 saveCookie函数  cookis的value设置编码出错！",e);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 清空所有cookie
	 */
	public void clearAllCookie(HttpServletRequest req,HttpServletResponse reb){
		 Cookie[] cookies = req.getCookies();   
		 try {   
			 for(int i=0;i<cookies.length;i++) {
				Cookie cookie = new Cookie(cookies[i].getName(), null);
				cookie.setMaxAge(0);
				cookie.setPath("/");//根据你创建cookie的路径进行填写
				reb.addCookie(cookie);
			 }
	      }catch(Exception ex)   
	      {   
	           System.out.println("清空所有Cookies发生异常！");   
	      }    
	}
	
}
