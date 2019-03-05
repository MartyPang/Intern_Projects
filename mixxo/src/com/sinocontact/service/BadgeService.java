package com.sinocontact.service;

import com.sinocontact.dao.BadgeDao;

/**
 * 
 * @Description: 集章Service
 * @CreateTime: 2016-7-18 下午5:37:46
 * @author: Martin Pang
 * @version V1.0
 */
public class BadgeService {

	private BadgeDao badgeDao;
	
	/**
	 * @Description: 用户上传一个小票号，增加一个集章
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-12 上午10:13:33
	 * @param telephone
	 * @param serialNumber  小票号
	 * @param receipPath    小票图片路径
	 * @return  101                           上传成功，但是有效小票数小于4
	 *          102                           上传失败
	 *          103                           小票号重复
	 *          104                           当日已成功上传过小票
	 *          105                          成功上传并且自动获得一张优惠券
	 */
	public int addBadge(String user_id, String telephone, String serial_number, String img_path){
		badgeDao = new BadgeDao();
		return badgeDao.addBadge(user_id, telephone, serial_number, img_path);
	}
	
	/**
	 * 
	 * @Description: 获取该手机用户上传有效小票数（valid_status = 1）
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-12 下午12:53:39
	 * @return
	 */
	public String currentValidSerialCount(String telephone){
		badgeDao = new BadgeDao();
		return badgeDao.currentValidSerialCount(telephone).toString();
	}
}
