package com.sinocontact.action;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sinocontact.common.CookieManager;
import com.sinocontact.common.PropertyUtils;
import com.sun.org.apache.xerces.internal.impl.PropertyManager;

public class BaseAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{
	private HttpServletRequest request=ServletActionContext.getRequest();
	protected  HttpServletResponse response=ServletActionContext.getResponse();
	/**
	 * 设置HttpServletRequest
	 */
	public void setServletRequest(HttpServletRequest req) {
		this.request = req;
	}

	/**
	 * 设置HttpServletResponse
	 */
	public void setServletResponse(HttpServletResponse resp) {
		this.response = resp;
	}
	
	/**
	 * 获取HttpServletRequest
	 */
    public HttpServletRequest getRequest()
	{
		return request;
	}
    
    
    /**
	 * 获取HttpServletResponse
	 */
	public HttpServletResponse getResponse()
	{
		return response;
	}
	/**
	 * 设置RequestAttribute
	 * @param name 参数名
	 * @param value 参数值
	 */
	public void setRequestAttribute(String name,Object value)
	{
		request.setAttribute(name, value);
	}
	
	/**
	 * 设置SessionAttribute
	 * @param name 参数名
	 * @param value 参数值
	 */
	public void setSessionAttribute(String name,Object value)
	{
		request.getSession().setAttribute(name, value);
	}

	/**
	 * 获取SessionAttribute
	 * @param name 参数名
	 * @param value 参数值
	 */
	public Object getSessionAttribute(String key)
	{
		return request.getSession().getAttribute(key);
	}
	
	/**
	 * 获取RequestAttribute
	 * @param name 参数名
	 * @param value 参数值
	 */
	public Object getRequestAttribute(String key)
	{
		return request.getAttribute(key);
	}
	
	
	
	private static final Logger logger = Logger.getLogger(BaseAction.class);
	public BaseAction(){
		
	}
	
	//退出登录
	public void quitLogin(){
		CookieManager.clearCurrentSession(getHttpServletResponse());
	}
	
	//用户是否登录
	public boolean isUserLogined(){
		return CookieManager.IsLogined(getHttpServletRequest());
	}
	public HttpServletRequest getHttpServletRequest() {
		return ServletActionContext.getRequest();
	}
	
	public HttpServletResponse getHttpServletResponse() {
		return ServletActionContext.getResponse();
	}
	
	public HashMap getUserCookieInfo(){
		return CookieManager.getUserCookieInfo(getHttpServletRequest());
	}
	
	//初始化用户失效时间
	public void initUserExpire(){
		//登录失效时间
		int expiry = Integer.parseInt(PropertyUtils.getProperty("login.expiry"));		
		CookieManager.saveCurrentUserID(CookieManager.getCurrentUserID(this.getHttpServletRequest()), expiry, this.getHttpServletResponse());
	}
	
	//是否是合法的用户
	public boolean isValidUser(){
		try{
			//得到当前操作的action类的类名
			String actionName = this.getClass().getName();
			//得到当前用户的登录信息
			HashMap userMap = CookieManager.getUserCookieInfo(getHttpServletRequest());
			if(userMap!=null){
				//得到当前用户的类型(0：表示油站用户，10：表示审核者，100：表示管理员)				
				if(userMap.get("usertype")!=null){
					return true;
				}
				
			}
		}catch(Exception e){
			logger.error("比较函数isValidUser出现异常！",e);
		}
		
		return false; 
	}
	
	//设置需要输出到jsp页面的变量
	public void putObject(String key,Object value){
		ActionContext.getContext().put(key, value);		
	}
	//得到输出到jsp页面的变量
	public Object getObject(String key){
		return ActionContext.getContext().get(key);		
		
	}
	
	//获取form或url中的参数值
	public String getParam(String key){
		String param = ServletActionContext.getRequest().getParameter(key);
		return param;
		
	}
	

	//返回值：0:表示相等；1表示Date1的日期在Date2的后面；-1表示Date1的日期在Date2的前面
	public int compareDate(String Date1, String Date2) {
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(Date1);
            Date dt2 = df.parse(Date2);
            if (dt1.getTime() > dt2.getTime()) {
            	logger.info("日期："+Date1+"在"+Date2+"后");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
            	logger.info("日期："+Date1+"在"+Date2+"前");
                return -1;
            } else {
            	logger.info("日期："+Date1+"和"+Date2+"相同");
                return 0;
            }
        } catch (Exception e) {
            
            logger.error("比较函数compareDate出现异常！",e);
        }
        return 0;
    }
	
	
	
	
	
		private String code;
		private String state;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
}
