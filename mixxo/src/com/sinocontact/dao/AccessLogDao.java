package com.sinocontact.dao;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.sinocontact.util.DateUtils;
import com.sinocontact.util.DbControl;

/**
 * 
 * @Description: 和访问日志有关的Dao操作
 * @author: Alen Gu 
 * @CreateTime: 2016-7-14
 */

public class AccessLogDao extends BaseDao{
	private static final Logger logger = Logger.getLogger(AccessLogDao.class);
	/**
	 * 
	 * @Description: 插入一条访问记录
	 * @author: Alen Gu 
	 * @CreateTime: 2016-7-14
	 * @param open_id
	 *        nickname
	 * @return
	 */
	public boolean insertAccessLog(String open_id, String nickname){
		Connection conn = null;
		String sql  = "";
		boolean flag = false;
		
		try{
			conn = DbControl.getConnection();
			sql = "insert into mixxo_access_log(openid,nickname,access_time) values(?,?,?)";
			flag = (getQueryRunner().update(conn, sql, open_id,nickname,DateUtils.getCurrentDateTime()))>0?true:false;
            DbControl.closeConnection(conn);		
		}catch(Exception e){
			logger.info("AccessLogDao中insertAccessLog函数捕获异常，Error: ",e);
		}finally{
			DbControl.closeConnection(conn);
		}
		return flag;
	}

}
