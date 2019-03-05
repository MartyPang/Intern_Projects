package com.sinocontact.action;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;



import com.sinocontact.thread.GetWXMessage;
import com.sinocontact.util.Base64Code;
import com.sinocontact.util.PropertyUtils;
import com.sinocontact.util.SHA1;
import com.sinocontact.dao.AccessLogDao;
import com.sinocontact.dao.DrawCardDao;
import com.sinocontact.dao.UserDao;
import com.sinocontact.service.WechatService;
import com.sinocontact.util.CookieManager;
import com.sun.org.apache.xml.internal.security.utils.Base64;


/**
 * 
 * @Description: 微信授权Action
 * @CreateTime: 2016-7-12 下午2:21:17
 * @author: Martin Pang
 * @version V1.0
 */
public class StartAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(StartAction.class);
	private static String jsapi_ticket="";
	private static CookieManager cookie=  new CookieManager();
	public GetWXMessage wx=new GetWXMessage();
	public AccessLogDao accessLogDao = new AccessLogDao();
	
	String telephone;
	
	/**
	 * 
	 * @Description: 打开我要集章链接时的授权
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-14 下午5:32:12
	 * @return
	 */
	public String preStart(){
		getSHA3();
		if(goback())
		{
			return "success";
		}
		logger.info("---------------------------------------------------------------------微信授权函数入口！");
		Map<String,Object> wechatUserInfo=null;
		wechatUserInfo = getWeiXin();
		logger.info("---------------------------------------------------------------------wechatUserInfo="+wechatUserInfo);
		
		if(wechatUserInfo!=null){
			getRequest().getSession().setAttribute("wechatUserInfo", wechatUserInfo);
			return "success";
		}else{
			return "login";
		}
	}
	
	/**
	 * 
	 * @Description: 用户点击返回按钮，判断code是否为空
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-21 下午2:30:17
	 * @return
	 */
	public boolean goback()
	{
		String tmp_code = cookie.getCookieValues(getRequest(), "the_code");
		logger.info("tmp_code++++"+tmp_code);
		if(tmp_code == null|| tmp_code =="")
		{
			return false;
		}
		return true;
	}
	public boolean goback2()
	{
		String second_code = cookie.getCookieValues(getRequest(), "second_code");
		logger.info("second_code++++"+second_code);
		if(second_code == null|| second_code =="")
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @Description: 点击分享链接时的微信授权
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-15 下午1:36:50
	 * @return
	 */
	public String mediumLink(){
		logger.info("---------------------------------------------------------------------mediumLink入口！");
		//////////////////////////////////////////////
		
		String from_id = this.getParam("from_id");
		getSHA5(from_id);
		DrawCardDao draw = new DrawCardDao();
		UserDao user = new UserDao();
		String tel = user.getTelByOpenid(from_id);
		String draw_num = draw.getAllValidDrawCard(tel).toString();
		logger.info(from_id+" "+draw_num);
		///////////////////////////////////////
		if(goback2()){
			getRequest().setAttribute("from_id", from_id);
			getRequest().setAttribute("draw_num", draw_num);
			getRequest().setAttribute("friend_openid", cookie.getCookieValues(getRequest(), "friend_openid"));
			logger.info("from_id: "+from_id);
			logger.info("draw_num: "+draw_num);
			logger.info("friend_openid: "+cookie.getCookieValues(getRequest(), "open_id"));
			return "success";
		}
		//////////////////////////////////////////
		Map<String,Object> wechatUserInfo=null;
		wechatUserInfo = getFriendWechat();
		logger.info("---------------------------------------------------------------------wechatUserInfo="+wechatUserInfo);
		

		if(wechatUserInfo!=null){
			getRequest().getSession().setAttribute("wechatUserInfo", wechatUserInfo);
			getRequest().setAttribute("from_id", from_id);
			getRequest().setAttribute("draw_num", draw_num);
			getRequest().setAttribute("friend_openid", wechatUserInfo.get("openid"));
			logger.info("from_id: "+from_id);
			logger.info("draw_num: "+draw_num);
			logger.info("friend_openid: "+wechatUserInfo.get("openid"));
			return "success";
		}else{
			return "login";
		}
	}
		
	/**
	 * 微信回调函数，获取用户微信id
	 */
	public Map<String,Object> getWeiXin(){
		Map<String,Object> wechatUserInfo=null;
		String openid="";
		String accessToken="";
		//判断code是否为空
		logger.info("getCode++++"+getCode());
		if(StringUtils.isNotEmpty(getCode())){
			WechatService wechatService=new WechatService();
			cookie.saveCookie(getResponse(), "the_code", getCode());
			//根据code获取access_token，和openid	
			Map<String,String> tokenMap=wechatService.getAccessToken(getCode());
			//判断token，和openid的map是否为空
			logger.info("tokenMap++++"+tokenMap);
			if(getSessionAttribute("tokenMap")!=null){               
				getRequest().getSession().removeAttribute("tokenMap");
			}
			getRequest().getSession().setAttribute("tokenMap", tokenMap);
			openid=tokenMap.get("openid");
			accessToken=tokenMap.get("access_token");
			//根据access_token，和openid获取微信用户详情
			if(StringUtils.isNotEmpty(accessToken)&&StringUtils.isNotEmpty(openid)){
				wechatUserInfo=wechatService.getWechatUserInfo(accessToken,openid);
			}
			logger.info("wechatUserInfo++++"+wechatUserInfo);

			//保存当前open_id到cookie中
			cookie.saveCookie(getResponse(), "open_id", openid);
			try {
				cookie.saveCookie(getResponse(), "nickname", Base64.encode(wechatUserInfo.get("nickname").toString().getBytes("UTF-8")));
				logger.info("getWinXin() nickname: "+Base64.encode(wechatUserInfo.get("nickname").toString().getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//logger.info("getWinXin() nickname: "+Base64Code.encode(wechatUserInfo.get("nickname").toString()));
			UserDao user = new UserDao();
			
			//if(user.getTelByOpenid(openid)!=null && user.getTelByOpenid(openid)!=""){
				setTelephone(user.getTelByOpenid(openid));
				getRequest().setAttribute("telephone", getTelephone());
				cookie.saveCookie(getResponse(), "telephone", telephone);
		        logger.info(telephone);
			//}
		    
		    
		    //登录信息保存到登录日志中
		    try {
				accessLogDao.insertAccessLog(openid, Base64.encode(wechatUserInfo.get("nickname").toString().getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return wechatUserInfo;
	}
	
	
	public String base64String(String tt){
		return Base64Code.encode(tt);
	}
	/**
	 * 微信回调函数，获取用户微信id
	 */
	public Map<String,Object> getFriendWechat(){
		Map<String,Object> wechatUserInfo=null;
		String friend_openid="";
		String accessToken="";
		//判断code是否为空
		logger.info("getCode++++"+getCode());
		if(StringUtils.isNotEmpty(getCode())){
			WechatService wechatService=new WechatService();
			cookie.saveCookie(getResponse(), "second_code", getCode());
			//根据code获取access_token，和openid	
			Map<String,String> tokenMap=wechatService.getAccessToken(getCode());
			//判断token，和openid的map是否为空
			logger.info("tokenMap++++"+tokenMap);
			if(getSessionAttribute("tokenMap")!=null){               
				getRequest().getSession().removeAttribute("tokenMap");
			}
			getRequest().getSession().setAttribute("tokenMap", tokenMap);
			friend_openid=tokenMap.get("openid");
			accessToken=tokenMap.get("access_token");
			//根据access_token，和openid获取微信用户详情
			if(StringUtils.isNotEmpty(accessToken)&&StringUtils.isNotEmpty(friend_openid)){
				wechatUserInfo=wechatService.getWechatUserInfo(accessToken,friend_openid);
			}
			logger.info("wechatUserInfo++++"+wechatUserInfo);

			//保存当前friend_openid到cookie中
			cookie.saveCookie(getResponse(), "friend_openid", friend_openid);
			try {
				cookie.saveCookie(getResponse(), "friend_nickname", Base64.encode(wechatUserInfo.get("nickname").toString().getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    //登录信息保存到登录日志中
		    try {
				accessLogDao.insertAccessLog(friend_openid, Base64.encode(wechatUserInfo.get("nickname").toString().getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return wechatUserInfo;
	}
	
	/**
	 * 
	 * @Description: 跳转至form.jsp
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-14 下午5:31:15
	 * @return
	 */
	public String jump(){
		getSHA1();
		return SUCCESS;
	}
	
	/**
	 * 
	 * @Description: 跳转至拼图页面
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-14 下午5:31:30
	 * @return
	 */
	public String jump2(){
		DrawCardDao draw = new DrawCardDao();
		String telephone = cookie.getCookieValues(getRequest(), "telephone");
		String count = draw.getAllValidDrawCard(telephone).toString();
		logger.info(telephone);
		logger.info("解锁图片数量为： "+count);
		getRequest().setAttribute("drawNum", count);
		getSHA2();
		return SUCCESS;
	}
	
	/**
	 * 
	 * @Description: 跳转到优惠券页面的微信授权
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-15 下午1:40:22
	 * @return
	 */
	public String getCouponList(){
		getSHA4();
		if(goback())
		{
			String open_id = cookie.getCookieValues(getRequest(), "open_id");
			UserDao user = new UserDao();
			String telephone = user.getTelByOpenid(open_id);
			getRequest().setAttribute("telephone",telephone);
			return "success";
		}
		logger.info("---------------------------------------------------------------------微信授权函数入口！");
		Map<String,Object> wechatUserInfo=null;
		wechatUserInfo = getWeiXin();

		logger.info("---------------------------------------------------------------------wechatUserInfo="+wechatUserInfo);
		
		if(wechatUserInfo!=null){
			getRequest().getSession().setAttribute("wechatUserInfo", wechatUserInfo);
			return "success";
		}else{
			return "login";
		}
	}
	
	/**
	 * 
	 * @Description: 跳转到form.jsp，获取签名等信息
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-15 下午1:39:15
	 */
	public void getSHA1(){

		logger.info("---------------------------------------------------------------------getSHA1！");
		//1.根据jsapi_ticket 、 noncestr（随机字符串，开发人员自己定义）、timestamp（时间戳，开发人员自己定义）
		//、URL（必须与要调用微信分享接口的URL一直，用拦截器保存URL，再在此处获取）
		jsapi_ticket = wx.getJsapi_ticket();
		String url=PropertyUtils.getProperty("url");//配置文件读取需要拼接URL的前面域名
		
		url += "/jump";
		String timestamp = PropertyUtils.getProperty("timestamp");//时间戳
		String noncestr = PropertyUtils.getProperty("noncestr");//随机字符串
		//String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		//String noncestr = UUID.randomUUID().toString().replace("-", "");
		
		String SHA1data = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url; 
		System.out.println("SHA1data"+SHA1data);
		logger.info("传回页面url="+url+"   SHA1data"+SHA1data);
		String signature = new SHA1().getDigestOfString(SHA1data.getBytes()); //SHA1签名
		String shareURL = PropertyUtils.getProperty("url")+"/myBadge.jsp";
		
		//signature 为 SHA1data 进过SHA1加密后的签名，将签名转发至页面
        //把参数传回页面
        getRequest().setAttribute("signature", signature);
        getRequest().setAttribute("timestamp", timestamp);
        getRequest().setAttribute("noncestr", noncestr);
        getRequest().setAttribute("appid", PropertyUtils.getProperty("appid"));
        getRequest().setAttribute("shareURL", shareURL);
        //getRequest().getSession().getAttribute("wechatUserInfo");
        //日志输出各种签名信息
        logger.info("signature: "+signature);
        logger.info("timestamp: "+timestamp);
        logger.info("noncestr: "+noncestr);
        logger.info("appid: "+PropertyUtils.getProperty("appid"));
	}

	/**
	 * 
	 * @Description: 跳转到拼图界面时，获得签名等信息
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-15 下午1:41:35
	 */
	public void getSHA2(){

		logger.info("---------------------------------------------------------------------getSHA2！");
		//1.根据jsapi_ticket 、 noncestr（随机字符串，开发人员自己定义）、timestamp（时间戳，开发人员自己定义）
		//、URL（必须与要调用微信分享接口的URL一直，用拦截器保存URL，再在此处获取）
		jsapi_ticket = wx.getJsapi_ticket();
		String url=PropertyUtils.getProperty("url");//配置文件读取需要拼接URL的前面域名
		
		url += "/jump2";
		String timestamp = PropertyUtils.getProperty("timestamp");//时间戳
		String noncestr = PropertyUtils.getProperty("noncestr");//随机字符串
		//String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		//String noncestr = UUID.randomUUID().toString().replace("-", "");
		
		String SHA2data = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url; 
		System.out.println("SHA1data"+SHA2data);
		logger.info("传回页面url="+url+"   SHA2data"+SHA2data);
		String signature = new SHA1().getDigestOfString(SHA2data.getBytes()); //SHA1签名
		//signature 为 SHA1data 进过SHA1加密后的签名，将签名转发至页面
        //把参数传回页面
        getRequest().setAttribute("signature", signature);
        getRequest().setAttribute("timestamp", timestamp);
        getRequest().setAttribute("noncestr", noncestr);
        getRequest().setAttribute("appid", PropertyUtils.getProperty("appid"));
        //设置分享链接的URL
        //DrawCardDao draw = new DrawCardDao();
        String open_id = cookie.getCookieValues(getRequest(), "open_id");
        String shareURL = PropertyUtils.getProperty("url")+"/myDraw.jsp?from_id="+open_id;
        getRequest().setAttribute("shareURL", shareURL);
        
        //getRequest().getSession().getAttribute("wechatUserInfo");
        //日志输出各种签名信息
        logger.info("signature: "+signature);
        logger.info("timestamp: "+timestamp);
        logger.info("noncestr: "+noncestr);
        logger.info("appid: "+PropertyUtils.getProperty("appid"));
        logger.info("share link: "+shareURL);
	}
	
	/**
	 * 
	 * @Description: 跳转到home.jsp，获取签名等信息
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-15 下午1:39:15
	 */
	public void getSHA3(){

		logger.info("---------------------------------------------------------------------getSHA3！");
		//1.根据jsapi_ticket 、 noncestr（随机字符串，开发人员自己定义）、timestamp（时间戳，开发人员自己定义）
		//、URL（必须与要调用微信分享接口的URL一直，用拦截器保存RUL，再在此处获取）
		jsapi_ticket = wx.getJsapi_ticket();
		String url=PropertyUtils.getProperty("url");//配置文件读取需要拼接URL的前面域名
		
		url += "/preStart?code=";
		url += getCode();
		url += "&state=aa";
		String timestamp = PropertyUtils.getProperty("timestamp");//时间戳
		String noncestr = PropertyUtils.getProperty("noncestr");//随机字符串
		//String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		//String noncestr = UUID.randomUUID().toString().replace("-", "");
		
		String SHA1data = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url; 
		System.out.println("SHA1data"+SHA1data);
		logger.info("传回页面url="+url+"   SHA1data"+SHA1data);
		String signature = new SHA1().getDigestOfString(SHA1data.getBytes()); //SHA1签名
		//signature 为 SHA1data 进过SHA1加密后的签名，将签名转发至页面
		String shareURL = PropertyUtils.getProperty("url")+"/myBadge.jsp";
        //把参数传回页面
        getRequest().setAttribute("signature", signature);
        getRequest().setAttribute("timestamp", timestamp);
        getRequest().setAttribute("noncestr", noncestr);
        getRequest().setAttribute("appid", PropertyUtils.getProperty("appid"));
        getRequest().setAttribute("shareURL", shareURL);
        //getRequest().getSession().getAttribute("wechatUserInfo");
        //日志输出各种签名信息
        logger.info("signature: "+signature);
        logger.info("timestamp: "+timestamp);
        logger.info("noncestr: "+noncestr);
        logger.info("appid: "+PropertyUtils.getProperty("appid"));
        logger.info("share link: "+shareURL);
	}
	
	/**
	 * 
	 * @Description: 跳转到优惠券.jsp，获取签名等信息
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-15 下午1:39:15
	 */
	public void getSHA4(){

		logger.info("---------------------------------------------------------------------getSHA4！");
		//1.根据jsapi_ticket 、 noncestr（随机字符串，开发人员自己定义）、timestamp（时间戳，开发人员自己定义）
		//、URL（必须与要调用微信分享接口的URL一直，用拦截器保存RUL，再在此处获取）
		jsapi_ticket = wx.getJsapi_ticket();
		String url=PropertyUtils.getProperty("url");//配置文件读取需要拼接URL的前面域名
		
		url += "/couponList?code=";
		url += getCode();
		url += "&state=aa";
		String timestamp = PropertyUtils.getProperty("timestamp");//时间戳
		String noncestr = PropertyUtils.getProperty("noncestr");//随机字符串
		//String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		//String noncestr = UUID.randomUUID().toString().replace("-", "");
		
		String SHA1data = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url; 
		System.out.println("SHA1data"+SHA1data);
		logger.info("传回页面url="+url+"   SHA1data"+SHA1data);
		String signature = new SHA1().getDigestOfString(SHA1data.getBytes()); //SHA1签名
		//signature 为 SHA1data 进过SHA1加密后的签名，将签名转发至页面
		String shareURL = PropertyUtils.getProperty("url")+"/myBadge.jsp";
        //把参数传回页面
        getRequest().setAttribute("signature", signature);
        getRequest().setAttribute("timestamp", timestamp);
        getRequest().setAttribute("noncestr", noncestr);
        getRequest().setAttribute("appid", PropertyUtils.getProperty("appid"));
        getRequest().setAttribute("shareURL", shareURL);
        //getRequest().getSession().getAttribute("wechatUserInfo");
        //日志输出各种签名信息
        logger.info("signature: "+signature);
        logger.info("timestamp: "+timestamp);
        logger.info("noncestr: "+noncestr);
        logger.info("appid: "+PropertyUtils.getProperty("appid"));
        logger.info("share link: "+shareURL);
	}
	
	/**
	 * 
	 * @Description: 跳转到拼图界面时，获得签名等信息
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-15 下午1:41:35
	 */
	public void getSHA5(String fromid){

		logger.info("---------------------------------------------------------------------getSHA2！");
		//1.根据jsapi_ticket 、 noncestr（随机字符串，开发人员自己定义）、timestamp（时间戳，开发人员自己定义）
		//、URL（必须与要调用微信分享接口的URL一直，用拦截器保存URL，再在此处获取）
		jsapi_ticket = wx.getJsapi_ticket();
		String url=PropertyUtils.getProperty("url");//配置文件读取需要拼接URL的前面域名
		
		url += "/mediumLink?from_id="+fromid+"&code=";
		url += getCode();
		url += "&state=aa";
		String timestamp = PropertyUtils.getProperty("timestamp");//时间戳
		String noncestr = PropertyUtils.getProperty("noncestr");//随机字符串
		//String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		//String noncestr = UUID.randomUUID().toString().replace("-", "");
		
		String SHA2data = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url; 
		System.out.println("SHA1data"+SHA2data);
		logger.info("传回页面url="+url+"   SHA2data"+SHA2data);
		String signature = new SHA1().getDigestOfString(SHA2data.getBytes()); //SHA1签名
		//signature 为 SHA1data 进过SHA1加密后的签名，将签名转发至页面
        //把参数传回页面
        getRequest().setAttribute("signature", signature);
        getRequest().setAttribute("timestamp", timestamp);
        getRequest().setAttribute("noncestr", noncestr);
        getRequest().setAttribute("appid", PropertyUtils.getProperty("appid"));
        //设置分享链接的URL
        //DrawCardDao draw = new DrawCardDao();
        //String open_id = cookie.getCookieValues(getRequest(), "open_id");
        String shareURL = PropertyUtils.getProperty("url")+"/myDraw.jsp?from_id="+fromid;
        getRequest().setAttribute("shareURL", shareURL);
        
        //getRequest().getSession().getAttribute("wechatUserInfo");
        //日志输出各种签名信息
        logger.info("signature: "+signature);
        logger.info("timestamp: "+timestamp);
        logger.info("noncestr: "+noncestr);
        logger.info("appid: "+PropertyUtils.getProperty("appid"));
        logger.info("share link: "+shareURL);
	}
	
	/**
	 * 
	 * @Description: 跳转到home.jsp，获取签名等信息
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-15 下午1:39:15
	 */
	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
       
	
}
