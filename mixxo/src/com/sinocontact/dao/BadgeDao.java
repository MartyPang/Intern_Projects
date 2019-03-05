package com.sinocontact.dao;

import java.sql.Connection;
import java.util.Date;

import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;

import com.sinocontact.util.DateUtils;
import com.sinocontact.util.DbControl;

/**
 * 
 * @Description: 集章有关的dao操作
 * @CreateTime: 2016-7-12 下午5:39:14
 * @author: Martin Pang
 * @version V1.0
 */
public class BadgeDao extends BaseDao {

	
	private static final Logger logger = Logger.getLogger(BadgeDao.class);
	
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
	public int addBadge(String user_id, String telephone, String serialNumber, String img_path){
		Connection conn = null;
		String sql="";
		Long serialNumberCount = 0L;
		Long currentDayCount = 0L;
		String create_time="";
		try{
			conn = DbControl.getConnection();
			//判断该手机用户当日是否已经上传过小票，若是返回104；
			sql = "select count(*) from (select telephone,max(create_time)due from mixxo_serial_number where telephone="+telephone+" group by telephone) p where DATE(p.due)>=CURDATE()";
			currentDayCount = getQueryRunner().query(conn, sql, new ScalarHandler<Long>());
			if(currentDayCount>0){
				DbControl.closeConnection(conn);
				return 104;
			}
			
			//判断小票号是否重复，若是返回103；
			sql = "select count(*) from mixxo_serial_number where serial_number=?";
			serialNumberCount = getQueryRunner().query(conn, sql, new ScalarHandler<Long>(), serialNumber);
			//小票号重复
			if(serialNumberCount>0){
				DbControl.closeConnection(conn);
				return 103;
			}
			
			//插入记录
			sql = "insert into mixxo_serial_number(user_id,telephone,serial_number,receip_path,create_time,valid_status) values(?,?,?,?,?,?)";
			create_time = DateUtils.getCurrentDateTime();
			getQueryRunner().update(conn, sql, user_id, telephone,serialNumber,img_path,create_time,1);
			DbControl.closeConnection(conn);
			
			//判断该手机用户上传有效小票数（valid_status = 1）是否为4，若是自动添加一张优惠券，并将valid_status置为0
			if(currentValidSerialCount(telephone) == 4){
				if(addCoupon(user_id,telephone)){
					setStatusInvalid(telephone);
					return 105;
				}
			}
			
			//判断用户本月是否第一次上传小票，若是自动翻一张拼图
			if(checkAutoUnlock(telephone) == 0L){
				autoUnlock(user_id,telephone);
			}
			return 101;
		}catch(Exception e){
			logger.info("BadgeDao中addBadge函数捕获异常，Error: ",e);
		}finally{
			DbControl.closeConnection(conn);
		}
		return 102;
	}
	
	/**
	 * 
	 * @Description: 获取该手机用户上传有效小票数（valid_status = 1）
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-12 下午12:53:39
	 * @return
	 */
	public Long currentValidSerialCount(String telephone){
		Long count = 0L;
		Connection conn = null;
		String sql = "";
		
		 try{
			 conn = DbControl.getConnection();
			 sql = "select count(*) from mixxo_serial_number where telephone=? and valid_status=1";
			 count = getQueryRunner().query(conn, sql, new ScalarHandler<Long>(),telephone);
			 DbControl.closeConnection(conn);
			 return count;
		 }catch(Exception e){
			 logger.info("BadgeDao中currentValidSerialCount函数捕获异常，Error: ",e);
		 }finally{
			 DbControl.closeConnection(conn);
		 }
		 return -1L;//error code
	}
	
	/**
	 * 
	 * @Description: 为该手机用户添加一张优惠券
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-12 下午1:20:43
	 * @param telephone
	 * @return
	 */
	public boolean addCoupon(String user_id, String telephone){
		boolean flag = false;
		Connection conn = null;
		String sql = "";
		String coupon_number = "";//优惠券号
		String create_time = "";//优惠券发放时间
		String expire_time = "";//优惠券失效时间
		try{
			conn = DbControl.getConnection();
			sql = "insert into mixxo_user_coupon(user_id,telephone,coupon_number,status,create_time,expire_time) values(?,?,?,?,?,?)";
			//优惠券券号  系统当前时间以毫秒计 + telephone
			coupon_number = System.currentTimeMillis() + telephone;
			//优惠券发放时间
			create_time = DateUtils.getCurrentDate();
			//优惠券失效时间
			expire_time = DateUtils.addMonthsToStr(new Date(), 1);
			
			getQueryRunner().update(conn, sql, user_id, telephone,coupon_number,1,create_time,expire_time);
			DbControl.closeConnection(conn);
			flag = true;
		}catch(Exception e){
			logger.info("BadgeDao中addCoupon函数捕获异常，Error: ",e);
		}finally{
			DbControl.closeConnection(conn);
		}
		return flag;
	}
	
	/**
	 * @Description: 将兑换优惠券的四个小票valid_staus置为0
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-12 下午1:31:37
	 * @param telephone
	 * @return
	 */
	public boolean setStatusInvalid(String telephone){
		boolean flag = false;
		Connection conn = null;
		String sql = "";
		
		try{
			conn = DbControl.getConnection();
			sql = "update mixxo_serial_number set valid_status=0 where telephone=? and valid_status=1";
			getQueryRunner().update(conn, sql,telephone);
			DbControl.closeConnection(conn);
			flag = true;
		}catch(Exception e){
			logger.info("BadgeDao中setStatusInvalid函数捕获异常，Error: ",e);
		}finally{
			DbControl.closeConnection(conn);
		}
		return flag;
	}
	
	/**
	 * 
	 * @Description: 判断该月是否已经自动解锁一张拼图
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-12 下午5:40:58
	 * @param telephone
	 * @return
	 */
	public Long checkAutoUnlock(String telephone){
		Long count=  0L;
		Connection conn = null;
		String sql = "";
		
		try{
			conn = DbControl.getConnection();
			sql = "select count(*) from mixxo_draw_card where telephone=? and card_number=1 and YEAR(create_time)=YEAR(CURDATE()) and MONTH(create_time)=MONTH(CURDATE())";
			count = getQueryRunner().query(conn, sql,new ScalarHandler<Long>(), telephone);
			DbControl.closeConnection(conn);
			return count;
		}catch(Exception e){
			logger.info("BadgeDao中checkAutoUnlock函数捕获异常，Error: ",e);
		}finally{
			DbControl.closeConnection(conn);
		}
		return -1L;
	}
	
	/**
	 * 
	 * @Description: 系统自动为用户解锁一张拼图
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-12 下午5:42:01
	 * @param telephone
	 * @return
	 */
	public boolean autoUnlock(String user_id, String telephone){
		boolean flag = false;
		Connection conn = null;
		String sql = "";
		String create_time = "";
		try{
			conn = DbControl.getConnection();
			create_time = DateUtils.getCurrentDateTime();
			sql = "insert into mixxo_draw_card(user_id,telephone,card_number,friend_openid,create_time) values(?,?,?,?,?)";
			getQueryRunner().update(conn, sql,"0000",telephone,1,"0000",create_time);
			DbControl.closeConnection(conn);
			flag = true;
		}catch(Exception e){
			logger.info("BadgeDao中autoUnlock函数捕获异常，Error: ",e);
		}finally{
			DbControl.closeConnection(conn);
		}
		return flag;
	}
//	public static void main(String[] argv){
//		BadgeDao dao = new BadgeDao();
//		System.out.println(dao.addBadge("15000096289", "2016071111066289"));
//	}
}
