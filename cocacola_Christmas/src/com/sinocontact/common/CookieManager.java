package com.sinocontact.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import com.sinocontact.common.JsonUtils;

/**
 * 类名：CookieManager
 * 功能：cookie管理
 */
public class CookieManager {
	
	private static final Log logger = LogFactory.getLog(CookieManager.class);
	
	
	/*
	 * 功能：得到当前访问者的userID
	 * 放回值：返回当前访问者的userid；如果当前访问者还没有登录，则返回null 
	 */
	public static String getCurrentUserID(HttpServletRequest req){
		String userID = null;		
		userID = getCookieValue(req,ConstCommon.LK_LOGIN_USERID);				
		return userID;
	}
	
	
	/*
	 * 功能：当前访问的用户是否已经登录
	 * 返回值：false表示未登录；true表示已经登录
	 */
	public static boolean IsLogined(HttpServletRequest req){
		
		//如果没有获取到Cookie中的userid，则说明用户肯定没有登录过。
		if(getCurrentUserID(req) == null){
			return false;
		}		
		return true;	
	}
	
	/*
	 * 功能：清除当前用户 的session信息（包含sesssion id）
	 * 返回值：用户的昵称(如果没有找到，则返回null)
	 */
	public static void clearCurrentSession(HttpServletResponse res){
		try{
			Cookie sessionCookie = new Cookie(ConstCommon.LK_LOGIN_USERID, null); 		
			
			//通过设置过期时间来达到立即清除cookie的效果。
			sessionCookie.setMaxAge(0);
			sessionCookie.setPath("/");	
			res.addCookie(sessionCookie); 			
			
		}catch(Exception e){
			logger.error("CookieManager的clearCurrentSession函数出现异常：",e);
		}
	}
	
	/*
	 * 功能：设置用户在cookie中的信息 
	 * 参数：accessMap表示访问者要设置的信息
	 * 返回值：true表示成功，false表示失败
	 */
	public static boolean saveAccessCookieInfo(HttpServletResponse res,HashMap accessMap){
		//如果原来已经存在对应cookie，则会被覆盖。
		try{
			
			//推广者标记
			Cookie mcCookie = new Cookie("mc",URLencode((String)accessMap.get("mc")));
			//是否分享标记
			Cookie isshareCookie = new Cookie("isshare",URLencode((String)accessMap.get("isshare")));			
			
			mcCookie.setPath("/");
			isshareCookie.setPath("/");		
			
			res.addCookie(mcCookie);
			res.addCookie(isshareCookie);
	
			
		}catch(Exception e){
			logger.error("CookieManager的saveAccessCookieInfo函数出现异常：",e);
			return false;
		}
		
		return true;
	}
	/*
	 * 功能：得到当前访问的用户的信息
	 * 参数：req 
	 * 返回值：HashMap 访问者信息
	 */
	public static HashMap getAccessCookieInfo(HttpServletRequest req){
		HashMap accessMap = new  HashMap();
		try{
			accessMap.put("mc",URLdecode(getCookieValue(req,"mc")));
			accessMap.put("isshare",URLdecode(getCookieValue(req,"isshare")));			
		}catch(Exception e){
			logger.error("CookieManager的getAccessCookieInfo函数出现异常：",e);
		}
		return accessMap;
	}
	/*
	 * 功能：设置用户在cookie中的信息 
	 * 参数：userMap表示用户要设置的用户信息
	 * 返回值：true表示成功，false表示失败
	 */
	public static boolean saveUserCookieInfo(HttpServletResponse res,HashMap userMap){
		
		//如果原来已经存在对应cookie，则会被覆盖。
		try{
			
			Cookie usernameCookie = new Cookie("username",URLencode((String)userMap.get("username")));
			Cookie usershopCookie = new Cookie("usershop",URLencode((String)userMap.get("usershop")));
			Cookie usertypeCookie = new Cookie("usertype",URLencode((String)userMap.get("usertype")));
			Cookie userstaffnoCookie = new Cookie("userstaffno",URLencode((String)userMap.get("userstaffno")));
			
			
			usernameCookie.setPath("/");
			usershopCookie.setPath("/");
			usertypeCookie.setPath("/");
			userstaffnoCookie.setPath("/");
			
			res.addCookie(usernameCookie);
			res.addCookie(usershopCookie);
			res.addCookie(usertypeCookie);	
			res.addCookie(userstaffnoCookie);
			
		}catch(Exception e){
			logger.error("CookieManager的saveUserCookieInfo函数出现异常：",e);
			return false;
		}
		
		return true;
	}	
	
	/*
	 * 功能：得到当前访问的用户的信息
	 * 参数：req 
	 * 返回值：HashMap 用户信息
	 */
	public static HashMap getUserCookieInfo(HttpServletRequest req){
		HashMap userMap = new  HashMap();
		try{
			userMap.put("username",URLdecode(getCookieValue(req,"username")));
			userMap.put("usershop",URLdecode(getCookieValue(req,"usershop")));
			userMap.put("usertype",URLdecode(getCookieValue(req,"usertype")));
			userMap.put("userstaffno",URLdecode(getCookieValue(req,"userstaffno")));
		}catch(Exception e){
			logger.error("CookieManager的getUserCookieInfo函数出现异常：",e);
		}
		return userMap;
	}
	
	/*
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
	 * 保存当前用户的userID，并将其放入到Cookie中
	 * @param userID	用户id
	 * @param expiry    失效时间(秒)
	 * @param res		response对象
	 * @return			生成的唯一ID
	 */
	public static boolean saveCurrentUserID(String userID,int expiry,HttpServletResponse res){
		//如果原来已经存在对应cookie，则会被覆盖。
		boolean issuccess  = true;
		try{	
			
			Cookie sessionIDCookie = new Cookie(ConstCommon.LK_LOGIN_USERID,userID);		
			sessionIDCookie.setPath("/");
			sessionIDCookie.setMaxAge(expiry);
			res.addCookie(sessionIDCookie);
		}catch(Exception e){
			issuccess = false;
			logger.error("CookieManager的saveCurrentUserID函数出现异常：",e);
		}
		return issuccess;
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
			logger.error("BaseAction 类 saveCookie函数  cookis的value设置编码出错！",e);
			e.printStackTrace();
		}
		//设置cookie存在时间
		//cookie.setMaxAge(time);
		
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
