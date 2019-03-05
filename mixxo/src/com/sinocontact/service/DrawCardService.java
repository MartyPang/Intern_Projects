package com.sinocontact.service;

import com.sinocontact.dao.DrawCardDao;

/**
 * 
 * @Description: 拼图Service
 * @CreateTime: 2016-7-18 下午5:38:41
 * @author: Martin Pang
 * @version V1.0
 */
public class DrawCardService {

	private DrawCardDao drawCardDao;
	
	/**
	 * 
	 * @Description: 解锁一张拼图
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-20 下午2:00:34
	 * @param user_id
	 * @param telephone
	 * @param card_number
	 * @param friend_openid
	 * @return
	 */
	public boolean pieceUnlock(String user_id, String telephone, String card_number,String friend_openid) {
		drawCardDao = new DrawCardDao();
		return drawCardDao.pieceUnlock(user_id, telephone, card_number, friend_openid);
	}
	
    /**
     * 
     * @Description: 根据手机号获得当前已解锁的拼图数量
     * @author: Martin Pang 
     * @CreateTime: 2016-7-20 下午2:01:02
     * @param telephone
     * @return
     */
	public String currentCardCount(String telephone){
		drawCardDao = new DrawCardDao();
		return drawCardDao.getAllValidDrawCard(telephone).toString();
	}
	
	/**
	 * 
	 * @Description: 检测该月是否已经解锁9张拼图
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-20 下午2:01:31
	 * @param telephone
	 * @return
	 */
	public boolean checkDrawCardFinished(String telephone) {
		drawCardDao = new DrawCardDao();
		return drawCardDao.checkDrawCardFinished(telephone);
	}
	
	/**
	 * 
	 * @Description: 检测该好友是否可以帮忙解锁
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-20 下午2:02:00
	 * @param telephone
	 * @param friend_openid
	 * @return
	 */
	public boolean unlockAvailable(String telephone, String friend_openid) {
		drawCardDao = new DrawCardDao();
		return drawCardDao.unlockAvailable(telephone, friend_openid);
	}
}
