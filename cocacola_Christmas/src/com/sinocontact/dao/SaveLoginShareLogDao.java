package com.sinocontact.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.log4j.Logger;

import com.sinocontact.common.BasicRowProcessorFix;
import com.sinocontact.common.DateUtils;
import com.sinocontact.common.DbControl;


/**
 * 1.保存用户登录日志
 * 2.保存分享日志
 * @author Alvin
 */
public class SaveLoginShareLogDao extends BaseDao{
	private static final Logger logger = Logger.getLogger(SaveLoginShareLogDao.class);
	
	/**
	 * 保存用户登录日志
	 * @param open_id：    微信ID
	 * @param user_ip：    用户IP
	 * @param url：            用户URL
	 * @param from_user：分享来源
	 * @return
	 */
	public int saveLoginLog
	(String open_id,String user_ip,String url,int from_user,String nick_name){
		Connection conn=null;
		int i=0;
		Date date = new Date();
		String strDate = DateUtils.addDaysToStr(date, 0);
		
		String sql="";
		try {
			conn=DbControl.getConnection();
			sql="INSERT INTO coca_backgrounddata(open_id,user_ip,url,create_time,from_user,nick_name) VALUES(?,?,?,?,?,?)";
			i=getQueryRunner().update(conn,sql,open_id,user_ip,url,strDate,from_user,nick_name);
			DbUtils.closeQuietly(conn);
		} catch (Exception e) {
			logger.error("SaveLoginShareLog 类的 saveLoginLog 函数错误！",e);
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(conn);
		}
		return i;
	}
	
	/**
	 * 保存分享统计信息
	 * @param open_id：	      微信ID
	 * @param user_id：        用户ID
	 * @param url：                用户URL
	 * @param nick_name：    昵称
	 * @param head_imgurl：头像
	 * @param type：              类型
	 * @return
	 */
	public int saveShareInfo
	(String open_id,int user_id,String url,String nick_name,String head_imgurl,String type){
		Connection conn=null;
		int i=0;
		Date date = new Date();
		String strDate = DateUtils.addDaysToStr(date, 0);
		
		String sql="";
		try {
			conn=DbControl.getConnection();
			sql="INSERT INTO coca_share(open_id,user_id,url,create_time,nick_name,head_imgurl,type) VALUES(?,?,?,?,?,?,?)";
			i=getQueryRunner().update(conn,sql,open_id,user_id,url,strDate,nick_name,head_imgurl,type);
			DbUtils.closeQuietly(conn);
		} catch (Exception e) {
			logger.error("SaveLoginShareLog 类的 saveLoginLog 函数错误！",e);
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(conn);
		}
		return i;
	}
	
	/**
	 * 实时查询游戏今日的最高分
	 * @return
	 */
	public Map<String, Object> getMaxNum(){
		Connection conn=null;
		Map<String, Object> map=new HashMap<String, Object>();
		String sql="";
		Date date = new Date();
		String str = DateUtils.addDaysToStr(date, 0);
		String strDate = str.substring(0,10);
		try {
			conn=DbControl.getConnection();
			sql=" SELECT MAX(game_num) as game_num,MAX(createtime) as createtime,open_id,nick_name,head_imgurl FROM coca_gamerecord " +
					" where createtime like '%"+strDate+"%' ";
			map=getQueryRunner().query(conn,sql,new MapHandler(new BasicRowProcessorFix()));
			DbUtils.closeQuietly(conn);
		} catch (Exception e) {
			logger.error("SaveLoginShareLogDao类 getMaxNum函数异常："+e);
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(conn);
		}
		return map;
	}
	
	/**
	 * 查看最高分榜单
	 */
	public List<Map<String, Object>> getMaxNumOfDay(){
		Connection conn=null;
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		String sql="";
		try {
			conn=DbControl.getConnection();
			sql=" SELECT * from coca_gamemaxnum ORDER BY date_time DESC ";
			list=getQueryRunner().query(conn,sql,new MapListHandler(new BasicRowProcessorFix()));
			DbUtils.closeQuietly(conn);
		} catch (Exception e) {
			logger.error("ProductDetailsDao类 getProductDetailsByCarDetails函数异常："+e);
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(conn);
		}
		return list;
	}

	/**
	 * 查询指定日期内，所有参与过游戏的人
	 * @return
	 */
	public List<Map<String, Object>> getPeople(){
		Connection conn=null;
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		String sql="";
		Date date = new Date();
		String str = DateUtils.addDaysToStr(date, -1);
		String strDate = str.substring(0,10);
		try {
			conn=DbControl.getConnection();
			sql=" SELECT open_id FROM coca_gamerecord WHERE createtime like '%"+strDate+"%' GROUP BY open_id ";
			list=getQueryRunner().query(conn,sql,new MapListHandler(new BasicRowProcessorFix()));
			DbUtils.closeQuietly(conn);
		} catch (Exception e) {
			logger.error("ProductDetailsDao类 getPeople 函数异常："+e);
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(conn);
		}
		return list;
	}
	
	
	
	
	/**
	 * 查询指定日期内，所有参与过游戏的人的最高分
	 * @return
	 */
	public Map<String, Object> getMaxNumOfDayBypeople(String open_id){
		Connection conn=null;
		Map<String, Object> map=new HashMap<String,Object>();
		String sql="";
		Date date = new Date();
		String str = DateUtils.addDaysToStr(date, -1);
		String strDate = str.substring(0,10);
		try {
			conn=DbControl.getConnection();
			sql=" SELECT * FROM coca_gamerecord WHERE open_id='"+open_id+"' " +
					" and createtime like '%"+strDate+"%' ORDER BY game_num DESC LIMIT 0,1 ";
			map=getQueryRunner().query(conn,sql,new MapHandler(new BasicRowProcessorFix()));
			DbUtils.closeQuietly(conn);
		} catch (Exception e) {
			logger.error("ProductDetailsDao类 getMaxNumOfDayBypeople 函数异常："+e);
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(conn);
		}
		return map;
	}
	
	/**
	 * 保存前天所有参与游戏的人的最高分
	 * @return
	 */
	public int saveMaxNumOfDay
	(String lasttime,int game_num,String open_id,String nick_name,String head_imgurl){
		Connection conn=null;
		int i=0;
		String sql="";
		try {
			conn=DbControl.getConnection();
			sql=" INSERT INTO coca_gamenum(lasttime,game_num,open_id,nick_name,head_imgurl) VALUES(?,?,?,?,?) ";
			i=getQueryRunner().update(conn,sql,lasttime,game_num,open_id,nick_name,head_imgurl);
			DbUtils.closeQuietly(conn);
		} catch (Exception e) {
			logger.error("SaveLoginShareLog 类的 saveLoginLog 函数错误！",e);
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(conn);
		}
		return i;
	}
	
	/**
	 * 从最高分表里拿到每日的最高分
	 * @return
	 */
	public Map<String, Object> getGameMaxNum(){
		Connection conn=null;
		Map<String, Object> map=new HashMap<String, Object>();
		String sql="";
		Date date = new Date();
		String str = DateUtils.addDaysToStr(date, -1);
		String strDate = str.substring(0,10);
		try {
			conn=DbControl.getConnection();
			sql=" SELECT  game_num,lasttime,open_id,head_imgurl,nick_name FROM coca_gamenum WHERE lasttime LIKE '"+strDate+"%' ORDER BY game_num DESC LIMIT 0,1";
			map=getQueryRunner().query(conn,sql,new MapHandler(new BasicRowProcessorFix()));
			DbUtils.closeQuietly(conn);
		} catch (Exception e) {
			logger.error("SaveLoginShareLogDao类 getGameMaxNum函数异常："+e);
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(conn);
		}
		return map;
	}
	
	
	/**
	 * 保存每天的历史最高分
	 * @return
	 */
	public int saveGameMaxNum
	(String date_time,String open_id,String nick_name,String head_imgurl,int game_num){
		Connection conn=null;
		int i=0;
		String sql="";
		try {
			conn=DbControl.getConnection();
			sql=" INSERT INTO coca_gamemaxnum(date_time,open_id,nick_name,head_imgurl,game_num) VALUES(?,?,?,?,?) ";
			i=getQueryRunner().update(conn,sql,date_time,open_id,nick_name,head_imgurl,game_num);
			DbUtils.closeQuietly(conn);
		} catch (Exception e) {
			logger.error("SaveLoginShareLog 类的 saveGameMaxNum 函数错误！",e);
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(conn);
		}
		return i;
	}
	
	/**
	 * 查询用户记录
	 * @return
	 */
	public Map<String, Object> seachUserInfo(String open_id){
		Connection conn=null;
		Map<String, Object> map=new HashMap<String, Object>();
		String sql="";
		try {
			conn=DbControl.getConnection();
			sql=" SELECT * FROM coca_user WHERE open_id=? ";
			map=getQueryRunner().query(conn,sql,new MapHandler(new BasicRowProcessorFix()),open_id);
			DbUtils.closeQuietly(conn);
		} catch (Exception e) {
			logger.error("SaveLoginShareLogDao类 getGameMaxNum函数异常："+e);
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(conn);
		}
		return map;
	}
	
	/**
	 * 添加用户记录
	 * @return
	 */
	public int addUser(String open_id,String nick_name,String head_imgurl){
		Connection conn=null;
		int i=0;
		String sql="";
		Date date = new Date();
		String str = DateUtils.addDaysToStr(date, 0);
		try {
			conn=DbControl.getConnection();
			sql=" INSERT INTO coca_user(open_id,nick_name,head_imgurl,create_time) VALUES(?,?,?,?) ";
			i=getQueryRunner().update(conn,sql,open_id,nick_name,head_imgurl,str);
			DbUtils.closeQuietly(conn);
		} catch (Exception e) {
			logger.error("SaveLoginShareLog 类的 addUser 函数错误！",e);
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(conn);
		}
		return i;
	}
	
	/**
	 * 添加游戏记录
	 * @return
	 */
	public int addGameNum(String open_id,String nick_name,String head_imgurl,int game_num){
		Connection conn=null;
		int i=0;
		String sql="";
		Date date = new Date();
		String str = DateUtils.addDaysToStr(date, 0);
		try {
			conn=DbControl.getConnection();
			sql=" INSERT INTO coca_gamerecord(open_id,nick_name,head_imgurl,game_num,createtime) VALUES(?,?,?,?,?) ";
			i=getQueryRunner().update(conn,sql,open_id,nick_name,head_imgurl,game_num,str);
			DbUtils.closeQuietly(conn);
		} catch (Exception e) {
			logger.error("SaveLoginShareLog 类的 addGameNum 函数错误！",e);
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(conn);
		}
		return i;
	}
	
	/**
	 * 添加分享操作日志
	 * @param open_id：微信id
	 * @param url：分享的连接
	 * @param create_time：分享时间
	 * @param nick_name：昵称
	 * @param head_imgurl：头像
	 * @param type：分享的类型
	 * @return
	 */
	public int addShareLog(String open_id,String url,String nick_name,String head_imgurl,String type){
		Connection conn=null;
		int i=0;
		String sql="";
		Date date = new Date();
		String str = DateUtils.addDaysToStr(date, 0);
		try {
			conn=DbControl.getConnection();
			sql=" INSERT INTO coca_share(open_id,url,create_time,nick_name,head_imgurl,type) "+
				" VALUES(?,?,?,?,?,?) ";
			i=getQueryRunner().update(conn,sql,open_id,url,str,nick_name,head_imgurl,type);
			DbUtils.closeQuietly(conn);
		} catch (Exception e) {
			logger.error("SaveLoginShareLog 类的 addShareLog 函数错误！",e);
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(conn);
		}
		return i;
	}
	
	public static void main(String[] args) {
		/*Date date = new Date();
		String str = DateUtils.addDaysToStr(date, 0);
		String strDate = str.substring(0,10);
		System.out.println(strDate);*/
		
		SaveLoginShareLogDao saveLoginShareLogDao=new SaveLoginShareLogDao();
		System.out.println(saveLoginShareLogDao.getMaxNum());
		
	}
}
