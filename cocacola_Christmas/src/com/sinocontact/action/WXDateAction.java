package com.sinocontact.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;

import sun.util.logging.resources.logging;

import Thread.GetWXMessage;

import com.sinocontact.common.CookieManager;
import com.sinocontact.common.JsonUtils;
import com.sinocontact.common.PropertyUtils;
import com.sinocontact.common.Struts2Utils;
import com.sinocontact.service.GameService;
import com.sinocontact.service.WechatService;

public class WXDateAction extends BaseAction {
	
	private static final Logger logger = Logger.getLogger(WXDateAction.class);
	public CookieManager cookieManager = new CookieManager();
	public GetWXMessage wx=new GetWXMessage();
	public GameService gameService = new GameService();
	public static String jsapi_ticket="";
	
	String user_ip="";
	int from_user = 0;
	int mark=0;
	
	public String start(){
		if(this.getParam("from_user")!=null){
			from_user = Integer.parseInt(this.getParam("from_user"));
		}
		logger.info("start()----------------------------------------------------from_user="+from_user);
		getRequest().setAttribute("from_user", from_user);
		return "success";
	}

	/**
	 * 游戏有权 获得微信信息并跳转到游戏首页
	 * @return
	 */
	public String preStart(){
		logger.info("---------------------------------------------------------------------微信回调函数入口！");
		String open_id="";
		String nick_name="";
		String head_imgurl="";
		Map<String,Object> wechatUserInfo=null;
		Map<String,Object> userMap=null;
		wechatUserInfo = getWeiXin();
		logger.info("---------------------------------------------------------------------wechatUserInfo="+wechatUserInfo);
		user_ip = getIpAddress(getRequest());
		//获取用户IP地址
		getRequest().getSession().setAttribute("user_ip", user_ip);
		logger.info("preStart:from_user---------------------------------"+from_user);
		if(this.getParam("from_user")!=null){
			from_user =Integer.parseInt(this.getParam("from_user"));
		}else{
			from_user = 0;
		}
		mark=1;
		
		logger.info("preStart函数 from_user="+from_user+"   user_ip="+user_ip+"   wechatUserInfo="+wechatUserInfo+"  mark="+mark);
		if(wechatUserInfo!=null){
			open_id = wechatUserInfo.get("openid").toString();
			userMap = gameService.seachUserInfo(open_id);
			if(userMap==null){
				nick_name = wechatUserInfo.get("nickname").toString();
				head_imgurl = wechatUserInfo.get("headimgurl").toString();
				gameService.addUser(open_id, nick_name, head_imgurl);
			}
			getRequest().getSession().setAttribute("from_user", from_user);
			getRequest().getSession().setAttribute("wechatUserInfo", wechatUserInfo);
			getRequest().getSession().setAttribute("user_ip", user_ip);
			getRequest().getSession().setAttribute("mark", mark);//首页进入的标示
			getRequest().getSession().setAttribute("jumpMark", "游戏首页");
			return "success";
		}else{
			return "login";
		}
		
	}
	/**
	 *  
	 * 1.根据jsapi_ticket、noncestr、timestamp、URL根据SHA1算法获得签名字符串
	 * 2.获取用户的微信微信详细信息
	 * 3.保存参数跳转到首页
	 * @return
	 */
	public String getSHA1(){
		logger.info("---------------------------------------------------------------------getSHA1！");
		//1.根据jsapi_ticket 、 noncestr（随机字符串，开发人员自己定义）、timestamp（时间戳，开发人员自己定义）
		//、URL（必须与要调用微信分享接口的URL一直，用拦截器保存URL，再在此处获取）
		jsapi_ticket = wx.getJsapi_ticket();
		String url=PropertyUtils.getProperty("url");//配置文件读取需要拼接URL的前面域名
		if(getRequest().getSession().getAttribute("url")!=null){
			url += getRequest().getSession().getAttribute("url").toString(); //获取URL(域名 + 拦截器保存的URL)
		}
		String timestamp = PropertyUtils.getProperty("timestamp");//时间戳
		String noncestr = PropertyUtils.getProperty("noncestr");//随机字符串
		String SHA1data = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url; 
		System.out.println("SHA1data"+SHA1data);
		logger.info("传回页面url="+url+"   SHA1data"+SHA1data);
		String signature = new SHA1().getDigestOfString(SHA1data.getBytes()); //SHA1签名
		//signature 为 SHA1data 进过SHA1加密后的签名，将签名转发至页面
        //把参数传回页面
        logger.info("传回页面 from_user="+from_user+"   user_ip="+user_ip+"   wechatUserInfo="+getRequest().getSession().getAttribute("wechatUserInfo"));
        getRequest().setAttribute("signature", signature);
        getRequest().setAttribute("timestamp", timestamp);
        getRequest().setAttribute("noncestr", noncestr);
        getRequest().setAttribute("appid", PropertyUtils.getProperty("appid"));
        getRequest().getSession().getAttribute("wechatUserInfo");
        getRequest().setAttribute("from_user", 0);
        if(getRequest().getSession().getAttribute("jumpMark").toString().equals("游戏首页")){
        	return "gameHome";
        }else if(getRequest().getSession().getAttribute("jumpMark").toString().equals("开始游戏")){
        	Map<String, Object> maxNumMap = new HashMap<String, Object>();
        	if(getRequest().getSession().getAttribute("maxNumMap")!=null){
        		try {
        			maxNumMap = (Map<String, Object>) getRequest().getSession().getAttribute("maxNumMap");
				} catch (Exception e) {
					logger.info("getSHA1函数 类型转换失败！");
					e.printStackTrace();
				}
        	}
        	logger.info("getSHA1类的 maxNumMap="+maxNumMap);
        	getRequest().getSession().setAttribute("maxNumMap", maxNumMap);
        	return "gameStart";
        }else if(getRequest().getSession().getAttribute("jumpMark").toString().equals("游戏章程")){
        	return "instructions";
        }else if(getRequest().getSession().getAttribute("jumpMark").toString().equals("历史榜单")){
        	List<Map<String, Object>> list =new ArrayList<Map<String,Object>>();
        	
        	if(getRequest().getSession().getAttribute("list")!=null){
        		try {
					list = JsonUtils.parseJSON2List(getRequest().getSession().getAttribute("list").toString());
				} catch (Exception e) {
					logger.info("getSHA1 类的  lits 类型转换失败");
				}
        	}
        	logger.info("getSHA1 类的  list ="+list);
        	getRequest().getSession().setAttribute("list", list);
        	return "theHighest";
        }else{
        	return "error";
        }
	}
	
	/**
	 * 开始游戏
	 * @return
	 */
	public String gameStart(){
		//查询游戏当前最高分
		Map<String, Object> maxNumMap = new HashMap<String, Object>();
		maxNumMap = gameService.getMaxNum();
		logger.info("gameStart类的 maxNumMap="+maxNumMap);
		getRequest().getSession().setAttribute("maxNumMap", maxNumMap);
		getRequest().getSession().setAttribute("jumpMark", "开始游戏");
		return "success";
	}
	
	/**
	 * 跳转到游戏章程
	 * @return
	 */
	public String instructions(){
		getRequest().getSession().setAttribute("jumpMark", "游戏章程");
		return "success";
	}
	
	/**
	 * 查看历史榜单
	 */
	public String theHighest(){
		List<Map<String, Object>> list =new ArrayList<Map<String,Object>>();
		list = gameService.getMaxNumOfDay();
		logger.info("theHighest 类的  list ="+list);
		getRequest().getSession().setAttribute("list", JsonUtils.list2OJson(list));
		getRequest().getSession().setAttribute("jumpMark", "历史榜单");
		return "success";
	}
	
	/**
	 * 添加游戏记录
	 */
	public void addGameNum(){
		String open_id = this.getParam("open_id");
		String nick_name = this.getParam("nick_name");
		String head_imgurl = this.getParam("head_imgurl");
		int game_num = Integer.parseInt(this.getParam("game_num"));
		int i=0;
		i = gameService.addGameNum(open_id, nick_name, head_imgurl, game_num);
		if(i>0){
			Struts2Utils.renderJson("1");
		}else{
			Struts2Utils.renderJson("0");
		}
	}
	
	/**
	 * 添加分享操作的日志
	 */
	public void addShareLog(){
		String open_id = this.getParam("open_id");
		String url = this.getParam("url");
		String nick_name = this.getParam("nick_name");
		String head_imgurl = this.getParam("head_imgurl");
		String type = this.getParam("type");
		logger.info("分享操作的日志open_id="+" url="+url+" nick_name="+nick_name+" head_imgurl="+head_imgurl+" type="+type);
		gameService.addShareLog(open_id, url, nick_name, head_imgurl, type);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 微信回调函数，获取用户昵称，头像，微信id
	 */
	public Map<String,Object> getWeiXin(){
		Map<String,Object> wechatUserInfo=null;
		String openid="";
		String accessToken="";
		String nickname="";
		//判断code是否为空
		logger.info("getCode++++"+getCode());
		if(StringUtils.isNotEmpty(getCode())){
			WechatService wechatService=new WechatService();
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
				nickname = filterWechatEmojiString(wechatUserInfo.get("nickname").toString());
				wechatUserInfo.put("nickname", nickname);
			}
			logger.info("wechatUserInfo++++"+wechatUserInfo);
			System.out.println(wechatUserInfo.toString());
			getRequest().getSession().setAttribute("wechatUserInfo",wechatUserInfo);
		}
		return wechatUserInfo;
	}
	
	//字符串特殊字符转换
	public static String filterWechatEmojiString (String str) {
		if(StringUtils.isNotEmpty(str)){
			str=str.replaceAll("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", "");
		}
		return str;
	}
	
	
	/**
	 * 获取访问用户的ip地址
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) { 
		
	    String ip = request.getHeader("x-forwarded-for"); 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		  ip = request.getHeader("Proxy-Client-IP"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		  ip = request.getHeader("WL-Proxy-Client-IP"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		  ip = request.getHeader("HTTP_CLIENT_IP"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		  ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		  ip = request.getRemoteAddr(); 
		} 
		return ip; 
	} 

	public static void main(String[] args) {
		
	}
}
