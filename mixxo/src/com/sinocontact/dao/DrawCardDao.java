package com.sinocontact.dao;

import java.sql.Connection;

import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;

import com.sinocontact.util.DateUtils;
import com.sinocontact.util.DbControl;

/**
 * 
 * @Description: 处理拼图有关的dao操作
 * @CreateTime: 2016-7-12
 * @author: Alen gu
 * @version V1.0
 */

public class DrawCardDao extends BaseDao {
	private static final Logger logger = Logger.getLogger(DrawCardDao.class);

	/**
	 * 
	 * @Description: 返回已经被解锁的拼图编号
	 * @author: Alen gu
	 * @CreateTime: 2016-7-12
	 * @param telephone
	 * @return
	 */
	public Long getAllValidDrawCard(String telephone) {
		//List<Integer> couponList = null;
		Long count = 0L;
		Connection conn = null;
		String sql = "";
		try {
			//couponList = new ArrayList<Integer>();
			conn = DbControl.getConnection();
			//从数据库中获取该用户该月所有解锁的拼图编号
			sql = "select count(card_number) from mixxo_draw_card where telephone=? and YEAR(create_time)=YEAR(CURDATE()) and MONTH(create_time)=MONTH(CURDATE())";

			count = getQueryRunner().query(conn, sql,
					new ScalarHandler<Long>(), telephone);
			DbControl.closeConnection(conn);
			return count;
		} catch (Exception e) {
			logger.info("DrawCardDao中getAllValidDrawCard函数捕获异常，Error: ", e);
		} finally {
			DbControl.closeConnection(conn);
			//couponList.clear();
		}
		return -1L;
	}

	/**
	 * @Description: 检查当月是否已经收集完成九个拼图碎片
	 * @author: Alen Gu
	 * @CreateTime: 2016-7-12
	 * @param telephone
	 * @return
	 */
	public boolean checkDrawCardFinished(String telephone) {
		Connection conn = null;
		String sql = "";
		Long count = 0L;
		boolean isFinished = false;

		try {
			conn = DbControl.getConnection();
			//从数据库中获取该用户该月所有解锁的拼图碎片数量  9为完成解锁
			sql = "select count(*) from mixxo_draw_card where telephone="
					+ telephone
					+ "and YEAR(create_time)=YEAR(CURDATE()) and MONTH(create_time)=MONTH(CURDATE())";
			count = getQueryRunner()
					.query(conn, sql, new ScalarHandler<Long>());
			if (count == 9L) {
				isFinished = true;
			}
			DbControl.closeConnection(conn);
		} catch (Exception e) {
			logger.info("DrawCardDao中checkDrawCardFinished函数捕获异常，Error: ", e);
		} finally {
			DbControl.closeConnection(conn);
		}
		return isFinished;
	}

	/**
	 * @Description: 该朋友是否可以帮忙解锁拼图碎片
	 * @author: Alen Gu
	 * @CreateTime: 2016-7-12
	 * @param telephone 
	 *        friend_openid
	 * @return  
	 */
	public boolean unlockAvailable(String telephone, String friend_openid) {
		boolean flag = false;
		Long count = 0L;
		Connection conn = null;
		String sql = "";
		try {
			
			conn = DbControl.getConnection();
			//查看该朋友是否可以帮忙解锁    1为解锁过   0为本月未解锁
			sql = "select count(*) from mixxo_draw_card where telephone= ? and friend_openid= ?  and YEAR(create_time)=YEAR(CURDATE()) and MONTH(create_time)=MONTH(CURDATE())";
			count = getQueryRunner().query(conn, sql, new ScalarHandler<Long>(),telephone,friend_openid);
			if (count == 0L) {
				flag = true;
			}
			DbControl.closeConnection(conn);
		} catch (Exception e) {
			logger.info("DrawCardDao中unlockAvailable函数捕获异常，Error: ", e);
		} finally {
			DbControl.closeConnection(conn);
		}
		return flag;
	}

	/**
	 * @Description: 解锁对应用户对应的拼图碎片
	 * @author: Alen Gu
	 * @CreateTime: 2016-7-12
	 * @param telephone
	 *        card_number 
	 *        friend_openid
	 * @return
	 */
	public boolean pieceUnlock(String user_id, String telephone, String card_number,String friend_openid) {
		boolean flag = false;
		Connection conn = null;
		String sql = "";
		String create_time = "";
		try {
			conn = DbControl.getConnection();
			create_time = DateUtils.getCurrentDateTime();
			//朋友帮忙解锁 数据库中插入一条相应记录
			sql = "insert into mixxo_draw_card(user_id,telephone,card_number,friend_openid,create_time) values(?,?,?,?,?)";
			getQueryRunner().update(conn, sql, user_id, telephone, card_number,
					friend_openid, create_time);
			flag = true;
			DbControl.closeConnection(conn);
		} catch (Exception e) {
			logger.info("DrawCardDao中pieceUnlock函数捕获异常，Error: ", e);
		} finally {
			DbControl.closeConnection(conn);
		}
		return flag;
	}

}
