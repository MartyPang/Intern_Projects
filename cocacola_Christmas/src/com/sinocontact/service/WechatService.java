package com.sinocontact.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.sinocontact.common.JsonUtils;
import com.sinocontact.common.PropertyUtils;

/**
 *处理微信相关接口的业务逻辑类
 */
public class WechatService {
	private static final Logger logger = Logger.getLogger(WechatService.class);

	// 组织get方式请求参数
	private String getParms(String returnStr, String key, String value) {
		if (StringUtils.isNotEmpty(returnStr)) {
			if (returnStr.contains("?")) {
				returnStr += "&" + key + "=" + value;
			} else {
				returnStr += "?" + key + "=" + value;
			}
		}
		return returnStr;
	}

	// get方式请求URL
	public String sendGet(String url) {
		logger.info("send get url:" + url);
		String result = "";
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 建立实际的连接
			conn.connect();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			logger.error("发送GET请求出现异常！", e);
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				logger.error("", ex);
			}
		}
		logger.info("send get result :" + result);
		return result;
	}
	
	/**
	 * 获取access_token
	 * @param code
	 * @return
	 */
	public Map<String,String> getAccessToken(String code){
		Map<String,String> tokenMap=new HashMap<String, String>();
		String accessToken="";
		String openid="";
		String url=PropertyUtils.getProperty("getTokenUrl");
		url=getParms(url,"appid",PropertyUtils.getProperty("appid"));
		url=getParms(url,"secret",PropertyUtils.getProperty("appsecret"));
		url=getParms(url,"code",code);
		url=getParms(url,"grant_type","authorization_code");
		String result=sendGet(url);
		try{
			if(StringUtils.isNotEmpty(result)){
				JSONObject jsonResult=new JSONObject(result);
				if(jsonResult.has("access_token")&&jsonResult.has("openid")){
					accessToken=jsonResult.getString("access_token");
					openid=jsonResult.getString("openid");
					tokenMap.put("access_token", accessToken);
					tokenMap.put("openid", openid);
				}
			}
		}catch(Exception e){
			logger.error("",e);
		}
		return tokenMap;
	}

	/**
	 * 根据access_token和openid获取用户详情
	 * @param accessToken
	 * @param openid
	 * @return
	 */
	public Map<String, Object> getWechatUserInfo(String accessToken,String openid) {
		Map<String, Object> userInfo=new HashMap<String, Object>();
		String url=PropertyUtils.getProperty("getUserInfoUrl");
		url=getParms(url,"access_token",accessToken);
		url=getParms(url,"openid",openid);
		url=getParms(url,"lang","zh_CN");
		String result=sendGet(url);
		try{
			if(StringUtils.isNotEmpty(result)){
				JSONObject jsonResult=new JSONObject(result);
				/*if(jsonResult.get("privilege")!=null){
					jsonResult.remove("privilege");
				}*/
				userInfo=JsonUtils.parseJSON2Map(jsonResult.toString());
			}
			
		}catch(Exception e){
			logger.error("",e);
		}
		return userInfo;
	}
	
}
