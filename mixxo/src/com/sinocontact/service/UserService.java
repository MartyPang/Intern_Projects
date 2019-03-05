package com.sinocontact.service;

import org.apache.log4j.Logger;

import com.sinocontact.dao.UserDao;

/**
 * @Description: 用户Service
 * @CreateTime: 2016-7-12 下午2:54:01
 * @author: Martin Pang
 * @version V1.0
 */
public class UserService {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(UserService.class);
	private UserDao userDao;
	
	/**
	 * 
	 * @Description:检查该手机号是否已经注册
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-20 下午1:56:37
	 * @param telephone
	 * @return
	 */
	public boolean checkTelephone(String telephone){
		userDao = new UserDao();
		return userDao.checkTelephone(telephone);
	}
	
	
	/**
	 * 
	 * @Description: 用户表中插入一个user
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-20 下午1:57:05
	 * @param open_id
	 * @param telephone
	 * @param nickname
	 * @return
	 */
	public boolean insertUser(String open_id, String telephone, String nickname){
		userDao = new UserDao();
		return userDao.insertUser(open_id, telephone,nickname);
	}
	
	/**
	 * 
	 * @Description: 检查该open_id是否已注册
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-20 下午1:57:42
	 * @param openid
	 * @return
	 */
	public boolean checkOpenid(String openid){
		userDao = new UserDao();
		return userDao.checkOpenid(openid);
	}

	/**
	 * 
	 * @Description: 根据open_id获得手机号
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-20 下午1:58:58
	 * @param open_id
	 * @return
	 */
	public String getTelByOpenid(String open_id) {
		// TODO Auto-generated method stub
		userDao = new UserDao();
		return userDao.getTelByOpenid(open_id);
	}

	
	/**
	 * 
	 * @Description: 根据open_id获得user_id
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-20 下午1:58:58
	 * @param open_id
	 * @return
	 */
	public String getUseridByOpenid(String open_id) {
		// TODO Auto-generated method stub
		userDao = new UserDao();
		return userDao.getUseridByOpenid(open_id);
	}
	
	public int saveSMSlog(String tel,String open_id,String user_ip,int status){
		userDao = new UserDao();
		return userDao.saveSMSlog(tel,open_id,user_ip,status);
	}
}
