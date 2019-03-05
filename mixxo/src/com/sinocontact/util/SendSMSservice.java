package com.sinocontact.util;

import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class SendSMSservice {
	
	private static final Logger logger = Logger.getLogger(SendSMSservice.class);

//	/**
//	 * 发送短信验证码
//	 */
//	public static void sendSMScode(){
//		String url=""; //发送地址
//		String appid="";
//		String appwd="";
//		String mobile=""; //手机号码
//		String content="";  // 短信模板/内容
//		String code="";   //验证码
//		String send_flag="";
//		String[] result;
//		//生成6位大于100000的随机数
//		Random random = new Random();
//	    int x = random.nextInt(899999);
//        x = x+100000;
//        code= x+"";
//        content = PropertyUtils.getProperty("contentReg").replace("${vcode}", code);
//        send_flag="0";
//        logger.info("sendSMScode发送短信函数,发送的验证码为："+code);
//        appid = PropertyUtils.getProperty("sms.appid");
//        appwd = PropertyUtils.getProperty("sms.apppwd");
//        url = PropertyUtils.getProperty("router.url");
//        mobile = "18221226028";
//        logger.info("mobile=========="+mobile);
//		result = sendSms(0,0L,url, appid, appwd, mobile, content);
//		System.out.println(result);
//	}
	/**
	 * 单条发送短信
	 * 
	 * 
	 * @param smsAppId
	 *            短信id（需要在短信网关中注册当前客户后获取）
	 * 
	 * @param smsAppPwd
	 *            短信密码（需要在短信网关中注册当前客户后获取）
	 * 
	 * @param phonenumber
	 *            手机号
	 * 
	 * @param content
	 *            短信内容
	 * @return 短信是否发送成功
	 */
	public String[] sendSms(Integer userId,Long pushId,String routerUrl,String smsAppId, String smsAppPwd,
			String phonenumber, String content)
	{
		String[] result=null;
		HttpClient client=null;
		PostMethod method=null;
		String sendResult="";
		try {
			String linkid=0+","+0;
			/**参数示例
			 * mt_deliver_id=1&password=ec2013&linkid=1,1&mobiles=18217775282&content=hi,this is zero
			 */
			NameValuePair[] nameValuePairs = new NameValuePair[5];
			nameValuePairs[0] = new NameValuePair("mt_deliver_id", smsAppId);
			nameValuePairs[1] = new NameValuePair("password", URLEncoder.encode(smsAppPwd,"utf-8"));
			nameValuePairs[2] = new NameValuePair("linkid", URLEncoder.encode(linkid,"utf-8"));
			nameValuePairs[3] = new NameValuePair("mobiles", phonenumber);
			nameValuePairs[4] = new NameValuePair("content", URLEncoder.encode(content,"utf-8"));
									
			client = new HttpClient();
			method = new PostMethod(routerUrl);
			method.setRequestBody(nameValuePairs);
			HttpMethodParams param = method.getParams();
			param.setContentCharset("UTF-8");
			client.executeMethod(method);			
			
			sendResult=method.getResponseBodyAsString();
			method.abort();
			param = null;
			method.releaseConnection();
			method = null;
			client = null;
			
			
			nameValuePairs = null;
			//解析发送结果
			result=parseSingleSendResult(sendResult,phonenumber);	
			
			
		}catch (Exception e) {
			result=parseSingleSendResult("-100,",phonenumber);
			logger.error("",e);
		}finally{
			try{
				if(method!=null){
					method.releaseConnection();
				}			
				method = null;
				client=null;
			}catch(Exception e1){
				logger.error("",e1);
			}
		}
		return result;
	}

	/**
	 * 解析发送结果
	 */
	private static String[] parseSingleSendResult(String sendResult,String phonenumber) {
		String[] result=new String[3];
		if(StringUtils.isEmpty(sendResult)){
			//提交失败
			return null;
		}else{
			String [] res=sendResult.split(",");
			if(res!=null&&res.length>0){
				if(res[0].equals("0")){//提交成功
					result[0]="0";
					result[1]=res[1];
					result[2]=phonenumber;
				}else{//提交失败
					return null;
				}
			}
		}
		return result;
	}

}
