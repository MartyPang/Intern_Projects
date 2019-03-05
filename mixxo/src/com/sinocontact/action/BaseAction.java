package com.sinocontact.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @Description: Action基类
 * @CreateTime: 2016-7-13 上午9:27:26
 * @author: Martin Pang
 * @version V1.0
 */
public class BaseAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request=ServletActionContext.getRequest();
	protected  HttpServletResponse response=ServletActionContext.getResponse();
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(BaseAction.class);
	public BaseAction(){
		
	}
	
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
	
	//获取form或url中的参数值
	public String getParam(String key){
		String param = ServletActionContext.getRequest().getParameter(key);
		return param;
		
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