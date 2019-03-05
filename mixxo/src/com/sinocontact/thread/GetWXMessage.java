package com.sinocontact.thread;

import org.apache.log4j.Logger;

import com.sinocontact.util.JsonUtils;
import com.sinocontact.util.PropertyUtils;
import com.sinocontact.service.WechatService;

public class GetWXMessage {
	private static final Logger logger = Logger.getLogger(GetWXMessage.class);
	private WechatService wechatService = new WechatService();
	public static String ACCESS_TOKEN="";
	public static String jsapi_ticket="";
	
	/**
	 * 
	 * ThreadStart类会跟随程序启动，并且调用Thread类；
	 * Thread类里面定义了定时器，用定时器调用该方法（每一小执行时一次，生产新的accessToken）
	 * 根据appId和appSecret来获取accessToken
	 * accessToken有效时间7200秒
	 * @return
	 */
	public void getAccessToken(){
		String appid="";
		String appSecret="";
		//从配置文件获得appId、appSecret
		appid = PropertyUtils.getProperty("appid");
		appSecret = PropertyUtils.getProperty("appsecret");
		//微信借口URL获得ACCESS_TOKEN
		String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appSecret+"";
		try {
			ACCESS_TOKEN = JsonUtils.parseJSON2Map(wechatService.sendGet(url)).get("access_token").toString();
		} catch (Exception e) {
			logger.error("WXDateAction 类 getAccessToken 函数 获取ACCESS_TOKEN异常！",e);
		}
		logger.info("ACCESS_TOKEN:"+ACCESS_TOKEN);
		System.out.println(ACCESS_TOKEN);
	}
	
	/**
	 *  
	 * 根据ACCESS_TOKEN调取微信借口来获取jsapi_ticket
	 * ThreadStart类会跟随程序启动，并且调用Thread类；
	 * Thread类里面定义了定时器，用定时器调用该方法（每一小执行时一次，生产新的jsapi_ticket）
	 * accessToken有效时间7200秒
	 * @return
	 */
	public String getJsapi_ticket(){
		System.out.println("ACCESS_TOKEN:"+ACCESS_TOKEN);
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ACCESS_TOKEN+"&type=jsapi";
		try {
			jsapi_ticket = JsonUtils.parseJSON2Map(wechatService.sendGet(url)).get("ticket").toString();
		} catch (Exception e) {
			logger.error("GetWXMessage 类 getJsapi_ticket 函数 获取jsapi_ticket异常！",e);
		}
		logger.info("jsapi_ticket:"+jsapi_ticket);
		System.out.println("jsapi_ticket:"+jsapi_ticket);
		return jsapi_ticket;
	}
	
	public static void main(String[] args) {
		
	}
	
}
