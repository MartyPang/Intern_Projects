package com.sinocontact.dao;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;

import com.sinocontact.util.BasicRowProcessorFix;
import com.sinocontact.util.DateUtils;
import com.sinocontact.util.DbControl;

/**
 * 
 * @Description: 处理用户有关的dao操作
 * @CreateTime: 2016-7-12 下午2:57:31
 * @author: Martin Pang
 * @version V1.0
 */
public class UserDao extends BaseDao {

	
	private static final Logger logger = Logger.getLogger(UserDao.class);
	/**
	 * @Description: 检查手机号是否已被注册
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-12 下午2:55:32
	 * @return
	 */
	public boolean checkTelephone(String telephone){
		Connection conn = null;
		String sql  = "";
		Long count = 0L;
		boolean isExisted = false;
		
		try{
			conn = DbControl.getConnection();
			sql = "select count(*) from mixxo_user where telephone="+telephone;
			count = getQueryRunner().query(conn, sql, new ScalarHandler<Long>());
			if(count>0){
				isExisted = true;
			}
			DbControl.closeConnection(conn);
		}catch(Exception e){
			logger.info("UserDao中checkTelephone函数捕获异常，Error: ",e);
		}finally{
			DbControl.closeConnection(conn);
		}
		return isExisted;
	}
	
	
	/**
	 * 
	 * @Description: 插入一个绑定手机号的用户
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-12 下午5:25:29
	 * @param open_id
	 * @param telephone
	 * @return
	 */
	public boolean insertUser(String open_id, String telephone, String nickname){
		Connection conn = null;
		String sql  = "";
		boolean flag = false;
		
		try{
			conn = DbControl.getConnection();
			sql = "insert into mixxo_user(open_id,telephone,nickname,create_time,status) values(?,?,?,?,?)";
			flag = (getQueryRunner().update(conn, sql, open_id,telephone,nickname,DateUtils.getCurrentDateTime(),1))>0?true:false;
            DbControl.closeConnection(conn);		
		}catch(Exception e){
			logger.info("UserDao中insertUser函数捕获异常，Error: ",e);
		}finally{
			DbControl.closeConnection(conn);
		}
		return flag;
	}
	
	public boolean checkOpenid(String openid){
		Connection conn = null;
		String sql  = "";
		boolean flag = false;
		
		try{
			conn = DbControl.getConnection();
			sql = "select count(*) from mixxo_user where open_id=?";
			flag = (getQueryRunner().query(conn, sql, new ScalarHandler<Long>(), openid))>0?true:false;
            DbControl.closeConnection(conn);	
		}catch(Exception e){
			logger.info("UserDao中checkOpenid函数捕获异常，Error: ",e);
		}finally{
			DbControl.closeConnection(conn);
		}
		return flag;
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
		Connection conn = null;
		String sql  = "";
		String tel = "";
		Map<String, Object> map = null;
		try{
			//该open_id用户还未注册
			if(!checkOpenid(open_id)){
				tel="";
				return tel;
			}
			map = new HashMap<String,Object>();
			conn = DbControl.getConnection();
			sql = "select telephone from mixxo_user where open_id=?";
			map = getQueryRunner().query(conn, sql, new MapHandler(new BasicRowProcessorFix()), open_id);
			tel = map.get("telephone").toString();
            DbControl.closeConnection(conn);	
		}catch(Exception e){
			logger.info("UserDao中getTelByOpenid函数捕获异常，Error: ",e);
		}finally{
			map = null;
			DbControl.closeConnection(conn);
		}
		return tel;
	}


	/**
	 * 
	 * @Description: 根据open_id获得user_id
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-13 下午5:53:04
	 * @param open_id
	 * @return
	 */
	public String getUseridByOpenid(String open_id) {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = "";
		String user_id = "";
		Map<String, Object> map = null;
		try{
			//该open_id用户还未注册
			if(!checkOpenid(open_id)){
				user_id="";
				return user_id;
			}
			map = new HashMap<String,Object>();
			conn = DbControl.getConnection();
			sql = "select user_id from mixxo_user where open_id=?";
			map = getQueryRunner().query(conn, sql, new MapHandler(new BasicRowProcessorFix()), open_id);
			user_id = map.get("user_id").toString();
            DbControl.closeConnection(conn);	
		}catch(Exception e){
			logger.info("UserDao中getUseridByOpenid函数捕获异常，Error: ",e);
		}finally{
			map = null;
			DbControl.closeConnection(conn);
		}
		return user_id;
	}


	/**
	 * 
	 * @Description: 保存短信日志
	 * @author: Martin Pang 
	 * @CreateTime: 2016-7-20 上午11:34:03
	 * @param tel
	 * @param open_id
	 * @param user_ip
	 * @param status
	 * @return
	 */
	public int saveSMSlog(String tel, String open_id, String user_ip,int status) {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql  = "";
		int i = 0;
		
		try{
			conn = DbControl.getConnection();
			sql = "insert into mixxo_sendsms_log(openid,user_ip,telephone,status,create_time) values(?,?,?,?,?)";
			i = getQueryRunner().update(conn, sql, open_id,user_ip,tel,status,DateUtils.getCurrentDateTime());
            DbControl.closeConnection(conn);		
		}catch(Exception e){
			logger.info("UserDao中saveSMSlog函数捕获异常，Error: ",e);
		}finally{
			DbControl.closeConnection(conn);
		}
		return i;
	}
}
