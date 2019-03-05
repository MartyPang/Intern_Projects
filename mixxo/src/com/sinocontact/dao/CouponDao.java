package com.sinocontact.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.log4j.Logger;

import com.sinocontact.util.BasicRowProcessorFix;
import com.sinocontact.util.DateUtils;
import com.sinocontact.util.DbControl;

/**
 * 
 * @Description: 处理优惠券有关的dao操作
 * @CreateTime: 2016-7-12 下午3:31:47
 * @author: Martin Pang
 * @version V1.0
 */
public class CouponDao extends BaseDao {

	private static final Logger logger = Logger.getLogger(CouponDao.class);
	
	/**
	 * 
	 * @Description: 用户使用一张优惠券
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-12 下午4:04:42
	 * @param telephone
	 * @param coupon_number
	 * @return
	 */
	public boolean useCoupon(String telephone, String coupon_number){
		Connection conn = null;
		String sql = "";
		boolean flag = false;
		
		try{
			conn = DbControl.getConnection();
			sql = "update mixxo_user_coupon set status=2 where telephone=? and coupon_number=?";
			getQueryRunner().update(conn, sql, telephone,coupon_number);
			DbControl.closeConnection(conn);
			
			conn = DbControl.getConnection();
			sql = "insert into mixxo_coupon_use_log(telephone,coupon_number,use_time) values(?,?,?)";
			getQueryRunner().update(conn, sql, telephone,coupon_number,DateUtils.getCurrentDateTime());
			DbControl.closeConnection(conn);
			
			flag = true;
		}catch(Exception e){
			logger.info("CouponDao中useCoupon函数捕获异常，Error: ",e);
			flag = false;
		}finally{
			DbControl.closeConnection(conn);
		}
		
		return flag;
	}
	
	/**
	 * 
	 * @Description: 根据status，返回优惠券
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-12 下午4:05:06
	 * @param telephone status
	 *        status=0       已过期
	 *        status=1       可使用
	 *        status=2       已使用
	 * @return
	 */
	public List<Map<String,Object>> getCouponList(String telephone, int status){
		//Map map = new HashMap<String, Object>();
		List<Map<String,Object>> couponList = null;
		Connection conn = null;
		String sql = "";
		try{
			couponList = new ArrayList<Map<String,Object>>();
			conn = DbControl.getConnection();
			//更新失效小票状态
			sql="update mixxo_user_coupon set status=0 where status=1 and telephone=? and DATE(expire_time)<=CURDATE()";
			getQueryRunner().update(conn,sql,telephone);
			DbControl.closeConnection(conn);
			
			//获取小票列表
			conn = DbControl.getConnection();
			sql = "select * from mixxo_user_coupon where telephone=? and status=?";
			couponList = getQueryRunner().query(conn, sql, new MapListHandler(new BasicRowProcessorFix()),telephone,status);
			DbControl.closeConnection(conn);
			return couponList;
		}catch(Exception e){
			logger.info("CouponDao中getCouponList函数捕获异常，Error: ",e);
		}finally{
			DbControl.closeConnection(conn);
			//couponList.clear();
		}
		return couponList;
	}
	
	/**
	 * 
	 * @Description: 每天凌晨，将所有已过期优惠券的status设置为0
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-12 下午5:46:19
	 */
	public void autoSetStatus()
	{
		Connection conn = null;
		String sql = "";
		
		try{
			conn = DbControl.getConnection();
			sql = "update mixxo_user_coupon set status=0 where DATE(expire_time)<=CURDATE()";
			getQueryRunner().update(conn, sql);
			logger.info("更新失效小票状态成功");
			DbControl.closeConnection(conn);
		}catch(Exception e){
			logger.info("CouponDao中autoSetStatus函数捕获异常，Error: ",e);
		}finally{
			DbControl.closeConnection(conn);
		}
	}
	
//	public static void main(String[] argv){
//		CouponDao coupon = new CouponDao();
//		System.out.println(coupon.getCouponList("15000096289", 1).size());
//	}
	
}
