package com.sinocontact.action;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sinocontact.service.BadgeService;
import com.sinocontact.service.CouponService;
import com.sinocontact.service.DrawCardService;
import com.sinocontact.service.UploadImgService;
import com.sinocontact.service.UserService;
import com.sinocontact.service.WechatService;
import com.sinocontact.thread.GetWXMessage;
import com.sinocontact.util.CookieManager;
import com.sinocontact.util.Struts2Utils;
import com.sinocontact.util.PropertyUtils;
import com.sinocontact.util.SendSMSservice;

/**
 * @Description: 提交表单的Action
 * @CreateTime: 2016-7-12 下午2:28:17
 * @author: Martin Pang
 * @version V1.0
 */
public class SubmitAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SubmitAction.class);
	
	private static CookieManager cookie = new CookieManager();
	private static SendSMSservice smsService = new SendSMSservice();
	private static UserService userService = new UserService();
	private static BadgeService badgeService = new BadgeService();
	private static DrawCardService drawCardService = new DrawCardService();
	private static CouponService couponService = new CouponService();
	WechatService wechatService=new WechatService();
	
	private String serial_number="";//小票号
	private String telephone="";//用户手机号
	private String validate_code="";//验证码
	private String img_path=""; //小票图片路径
	private String media_id="";//调用微信上传文件接口返回的id

	
	/**
	 * 
	 * @Description: 提交表单
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-13 上午11:45:34
	 */
	public void submit(){
		setSerial_number(getRequest().getParameter("serial_number"));
		img_path=UploadImgService.upload(GetWXMessage.ACCESS_TOKEN, media_id,serial_number);
		String code_tmp = "";
		String open_id = cookie.getCookieValues(getRequest(), "open_id");
		String nickname = cookie.getCookieValues(getRequest(), "nickname");
		logger.info("submit函数中获取的open_id "+open_id);
		logger.info("submit函数中获取的nickname "+nickname);
		//检测该小票号是否符合基本格式
		if(!isSerialNumber(getRequest().getParameter("serial_number"))){
			Struts2Utils.renderText("serialerror");
			return;
		}
		//判断该openid是否已绑定手机号
		if(!userService.checkOpenid(open_id)){
			code_tmp = getRequest().getSession().getAttribute("vcode").toString();
			setValidate_code(getRequest().getParameter("validate_code"));
			//判断手机验证码是否正确
			if(!code_tmp.equals(getValidate_code())){
				Struts2Utils.renderText("codeerror");
			}else{
				userService.insertUser(open_id, telephone, nickname);
				cookie.saveCookie(getResponse(), "telephone", telephone);
				String user_id = userService.getUseridByOpenid(open_id);
				int badgeRes = badgeService.addBadge(user_id,telephone, serial_number,img_path);
				String currentBadge = badgeService.currentValidSerialCount(telephone);
				String currentCard = drawCardService.currentCardCount(telephone);

				Struts2Utils.renderText(badgeRes+currentBadge+currentCard);
			}
		}
		else{
			setTelephone(userService.getTelByOpenid(open_id));
			cookie.saveCookie(getResponse(), "telephone", telephone);
			logger.info("submit函数中获取的telephone "+telephone);
			String user_id = userService.getUseridByOpenid(open_id);
			int badgeRes = badgeService.addBadge(user_id,telephone, serial_number,img_path);
			String currentBadge = badgeService.currentValidSerialCount(telephone);
			String currentCard = drawCardService.currentCardCount(telephone);
			Struts2Utils.renderText(badgeRes+currentBadge+currentCard);
		}
	}
	
	/**
	 * 
	 * @Description: 小票号基本格式 
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-21 下午2:27:27
	 * @param serial_num
	 * @return
	 */
	public static boolean isSerialNumber(String serial_num){
		Pattern p = Pattern.compile("^(M|m)(C|c)[A-Za-z0-9]{6}\\d{12}");  
		  
		Matcher m = p.matcher(serial_num);  
		  
		return m.matches(); 
	}
	
//	public static void main(String[] argv){
//		if(isSerialNumber("sadkjasd")){
//			System.out.println(1);
//		}else{
//			System.out.println(2);
//		}
//	}
	/**
	 * 
	 * @Description: 朋友帮忙解锁一块拼图
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-15 下午1:42:36
	 */
	public void unlockOnePiece(){
		String from_id = getRequest().getParameter("from_id");
		String friend_id = getRequest().getParameter("friend_id");
		String user_id = userService.getUseridByOpenid(from_id);
		String telephone = userService.getTelByOpenid(from_id);
		//String card_num="";
		
		int count = Integer.parseInt(drawCardService.currentCardCount(telephone).toString());
		//拼图解锁张数小于9，可以解锁一张
		if(count < 9){
			drawCardService.pieceUnlock(user_id, telephone, count+1+"", friend_id);
		}
		Struts2Utils.renderText("解锁成功");
	}
	
	/**
	 * 
	 * @Description: 检查该open_id是否已在表中存在
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-15 上午11:06:15
	 */
	public void checkOpenid(){
		String openid = cookie.getCookieValues(getRequest(),"open_id");
		boolean flag = userService.checkOpenid(openid);
		if(flag){
			Struts2Utils.renderText("true");
		}else{
			Struts2Utils.renderText("false");
		}
	}
	
	/**
	 * 
	 * @Description: 该朋友是否可以帮忙解锁拼图碎片
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-21 下午2:28:50
	 */
	public void checkUnlockable(){
		String from_id = getRequest().getParameter("from_id");
		String friend_id = getRequest().getParameter("friend_id");
		String telephone = userService.getTelByOpenid(from_id);
		logger.info("checkUnlockable函数 telephone: "+telephone);
		logger.info("friend_id: "+friend_id);
		String flag = drawCardService.unlockAvailable(telephone, friend_id)==true?"1":"0";
		if(from_id.equals(friend_id)){
			flag = "2";
		}
		Struts2Utils.renderText(flag);
	}
	
	/*
	 * 检查印章数量 0 - 3
	 */
	public void checkBadgeNumber(){
		String openid = cookie.getCookieValues(getRequest(),"open_id");
		String tel = userService.getTelByOpenid(openid);
		String num = badgeService.currentValidSerialCount(tel);
		Struts2Utils.renderText(num);
	}
	
	/**
	 * 发送短信验证码
	 */
	public void sendSMScode(){
		
		setTelephone(getRequest().getParameter("mobile"));
		String open_id = cookie.getCookieValues(getRequest(),"open_id");
		String user_ip = getIpAddress(getRequest());
		
		//判断手机号是否有效
		if(!isMobile(getTelephone())){
			Struts2Utils.renderText("手机号不合法！");
		}else{
			//判断手机号是否已经被注册
			if(userService.checkTelephone(getTelephone())){
				Struts2Utils.renderText("该手机号已被注册！");
			}else{
				String url=""; //发送地址
				String appid="";
				String appwd="";
				String mobile=""; //手机号码
				String content="";  // 短信模板/内容
				String code="";   //验证码
				String[] result;
				//生成6位大于100000的随机数
				Random random = new Random();
				int x = random.nextInt(899999);
				x = x+100000;
				code= x+"";
				//设置验证码值
				getRequest().getSession().setAttribute("vcode", code);
				setValidate_code(code);
				content = PropertyUtils.getProperty("contentReg").replace("${vcode}", code);
				logger.info("sendSMScode发送短信函数,发送的验证码为："+code);
				appid = PropertyUtils.getProperty("sms.appid");
				appwd = PropertyUtils.getProperty("sms.apppwd");
				url = PropertyUtils.getProperty("router.url");
				mobile = getTelephone();
				logger.info("mobile=========="+mobile);
				result = smsService.sendSms(0,0L,url, appid, appwd, mobile, content);

				//返回给ajax的参数
				if(result[0].equals("0")){
					//插入短信发送日志
					userService.saveSMSlog(getTelephone(), open_id, user_ip, 1);
					Struts2Utils.renderText("发送成功！");
				}else{
					userService.saveSMSlog(getTelephone(), open_id, user_ip, 0);
					Struts2Utils.renderText("发送失败！");
				}
			}
		}
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
	
	/**
	 * 
	 * @Description: 使用优惠券
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-15 下午3:42:09
	 * @return
	 */
	public void useCoupon(){
		String telephone = this.getParam("tel");
		String coupon_number = this.getParam("coupon_num");

		//logger.info("userCoupon")
		couponService.useCoupon(telephone, coupon_number);
		
	}
	
	//手机号检测
	public boolean isMobile(String mobiles){  
		//
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[6-8])|(18[0-9]))\\d{8}$");  
		  
		Matcher m = p.matcher(mobiles);  
		  
		return m.matches(); 
	}

	
	//getters and setters
	public String getSerial_number() {
		return serial_number;
	}

	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getValidate_code() {
		return validate_code;
	}

	public void setValidate_code(String validate_code) {
		this.validate_code = validate_code;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}
	
	
}
