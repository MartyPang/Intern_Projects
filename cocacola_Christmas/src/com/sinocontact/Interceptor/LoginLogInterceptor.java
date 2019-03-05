package com.sinocontact.Interceptor;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sinocontact.common.JsonUtils;
import com.sinocontact.dao.SaveLoginShareLogDao;
/**
 * 拦截器
 * 保存用户登录日志
 * @author sinocontact
 */
public class LoginLogInterceptor extends AbstractInterceptor {
	
	 private static final Logger logger = Logger.getLogger(LoginLogInterceptor.class);
	 SaveLoginShareLogDao saveLoginShareLogDao = new SaveLoginShareLogDao();
	 @Override
	 public String intercept(ActionInvocation invocation) throws Exception {
		 logger.info("用户登录日志拦截器启动:LoginLogInterceptor!");
		
		 HttpServletRequest req= ServletActionContext.getRequest();
		 
		 Map<String, Object> map = new HashMap<String, Object>();
		 String open_id="";//微信ID
		 String user_ip="";//用户IP
		 String url="";//此次访问的URL
		 String nick_name="";//昵称
		 int from_user=0;//分享来源
		 int i=0;
		 int mark=0;
		 try{
			 
			 if(invocation.getInvocationContext().getSession().get("mark")!=null ){
				 mark = Integer.parseInt(invocation.getInvocationContext().getSession().get("mark").toString());
			 }
			 //判断是否是从首页点进来的
			 logger.info("连拦截器wechatUserInfo----------"+invocation.getInvocationContext().getSession().get("wechatUserInfo"));
			 if(mark>0 && invocation.getInvocationContext().getSession().get("wechatUserInfo")!=null){
				 if(invocation.getInvocationContext().getSession().get("user_ip")==null || invocation.getInvocationContext().getSession().get("wechatUserInfo")==null || invocation.getInvocationContext().getSession().get("from_user")==null){
					 logger.info("启动拦截器  数据为空 添加数据失败！");
					 return invocation.invoke();
				 }else{
					 logger.info("登录日志拦截器正常进入！");
					 url =req.getRequestURI(); 
					 from_user =  (Integer) invocation.getInvocationContext().getSession().get("from_user");
					 user_ip = invocation.getInvocationContext().getSession().get("user_ip").toString();
					 map = (Map<String, Object>) invocation.getInvocationContext().getSession().get("wechatUserInfo");
					 open_id = map.get("openid").toString();
					 nick_name = map.get("nickname").toString();
					 logger.info("登录日志拦截数据  url："+url+"  from_user:"+from_user+"  user_ip:"+user_ip+"   openid:"+open_id);
					 /**
					  * 保存到数据库
					  */
					 i = saveLoginShareLogDao.saveLoginLog(open_id, user_ip, url, from_user,nick_name);
					 if(i>0){
						 logger.info("登录日志保存成功！");
					 }else{
						 logger.info("登录日志保存失败！");
					 }
					 return invocation.invoke();
				 }
			 }else{
				 return "login";
			 }
		 }catch (Exception e) {
			 return "error";
		 }
	 }

}
