package com.sinocontact.thread;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @Description: 未使用！！！
 * @CreateTime: 2016-7-18 上午9:39:38
 * @author: Martin Pang
 * @version V1.0
 */
public class SetCouponStatus extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SetCouponStatus(){
		super();
	}
	
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void init() throws ServletException {
		AutoSetCouponStatusThread th = new AutoSetCouponStatusThread();
		th.start();
	}
}
