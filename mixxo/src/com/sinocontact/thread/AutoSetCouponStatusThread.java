package com.sinocontact.thread;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.sinocontact.dao.CouponDao;

/**
 * 
 * @Description: 未使用的类！！！
 * @CreateTime: 2016-7-18 上午9:39:19
 * @author: Martin Pang
 * @version V1.0
 */
public class AutoSetCouponStatusThread extends Thread implements Runnable {

	CouponDao  couponDao = new CouponDao();
	
	@Override
	public void run() {
		 Calendar cal = Calendar.getInstance();
		 cal.set(Calendar.HOUR_OF_DAY, 0);
		 cal.set(Calendar.MINUTE, 30);
		 cal.set(Calendar.SECOND, 0);
		 Timer timer = new Timer();
	        timer.schedule(new TimerTask() {
	            public void run() {
	            	couponDao.autoSetStatus();
	            }
	        }, cal.getTime(), 1000*60*60*24);
	}
}
