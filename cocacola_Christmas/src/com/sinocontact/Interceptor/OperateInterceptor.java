package com.sinocontact.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 取得用户访问的URL地址保存到Cookie，以便生产SHA1签名
 * @author Alvin
 */

public class OperateInterceptor extends AbstractInterceptor {
    private String url="";
    private static final Logger logger = Logger.getLogger(OperateInterceptor.class);
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletResponse reb= ServletActionContext.getResponse();
		HttpServletRequest req=ServletActionContext.getRequest();
		url = req.getRequestURI(); 
		if(req.getQueryString()!=null){
			url +="?"+ req.getQueryString();
		}
		logger.info("拦截器保存的url为："+url);
		req.getSession().setAttribute("url", url);
		return invocation.invoke();
	}
}
