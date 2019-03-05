package com.sinocontact.service;

import com.sinocontact.dao.CouponDao;

/**
 * 
 * @Description: 优惠券Service
 * @CreateTime: 2016-7-18 下午5:38:23
 * @author: Martin Pang
 * @version V1.0
 */
public class CouponService {

	private CouponDao couponDao;
	
	/**
	 * 
	 * @Description: 使用一张优惠券
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-20 下午2:00:08
	 * @param telephone
	 * @param coupon_number
	 * @return
	 */
	public boolean useCoupon(String telephone, String coupon_number){
		couponDao = new CouponDao();
		return couponDao.useCoupon(telephone, coupon_number);
	}
}
