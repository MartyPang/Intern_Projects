package com.sinocontact.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sinocontact.dao.SaveLoginShareLogDao;


public class GameService extends BaseService {
	private static final Logger logger = Logger.getLogger(GameService.class);
	SaveLoginShareLogDao saveLoginShareLogDao = new SaveLoginShareLogDao();
	/**
	 * 实时查询游戏今日的最高分
	 * @return
	 */
	public Map<String, Object> getMaxNum(){
		Map<String, Object> map = new HashMap<String, Object>();
		map = saveLoginShareLogDao.getMaxNum();
		if(map == null){
			map.put("game_num", 0);
		}
		return map;
	}
	
	/**
	 * 查看最高分榜单
	 */
	public List<Map<String, Object>> getMaxNumOfDay(){
		List<Map<String, Object>> list =new ArrayList<Map<String,Object>>();
		list = saveLoginShareLogDao.getMaxNumOfDay();
		if(list == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("date", "暂无数据");
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 查询用户记录
	 * @return
	 */
	public Map<String, Object> seachUserInfo(String open_id){
		Map<String, Object> map = new HashMap<String, Object>();
		map = saveLoginShareLogDao.seachUserInfo(open_id);
		return map;
	}
	
	/**
	 * 添加用户记录
	 * @return
	 */
	public int addUser(String open_id,String nick_name,String head_imgurl){
		int i=0;
		i=saveLoginShareLogDao.addUser(open_id, nick_name, head_imgurl);
		return i;
	}
	
	/**
	 * 添加游戏记录
	 * @return
	 */
	public int addGameNum(String open_id,String nick_name,String head_imgurl,int game_num){
		int i=0;
		i=saveLoginShareLogDao.addGameNum(open_id, nick_name, head_imgurl,game_num);
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
	public void addShareLog(String open_id,String url,String nick_name,String head_imgurl,String type){
		int i=0;
		i=saveLoginShareLogDao.addShareLog(open_id, url, nick_name, head_imgurl, type);
		if(i>0){
			logger.info("分享日志添加成功！");
		}else{
			logger.info("分享日志添加失败！");
		}
	}
	
}
