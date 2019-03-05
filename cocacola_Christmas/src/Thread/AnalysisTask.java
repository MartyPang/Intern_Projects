package Thread;

import org.apache.log4j.Logger;

import com.sinocontact.dao.SaveLoginShareLogDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

/**
 * 每天晚上00:01时把每个人前一天的游戏最高分数加入数据库
 * @author Alvin
 */
public class AnalysisTask extends TimerTask {
	
    private static  final Logger logger = Logger.getLogger(AnalysisTask.class);
    SaveLoginShareLogDao saveLoginShareLogDao=new SaveLoginShareLogDao();
    @Override
    public void run() {
    	logger.info("-----------------------------自动添加每天每人游戏的最高分线程启动！");
    	List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
    	Map<String, Object> mapU = new HashMap<String, Object>();
    	List<Map<String, Object>> listP=new ArrayList<Map<String,Object>>();
    	Map<String, Object> map = new HashMap<String, Object>();
    	Map<String, Object> mapMaxNum = new HashMap<String, Object>();
    	String lasttime="";
    	int game_num=0;
    	String open_id="";
    	String nick_name="";
    	String head_imgurl="";
        Long s = System.currentTimeMillis();
        //查询昨天参与过游戏的所有人
        listP = saveLoginShareLogDao.getPeople();
        for(int i=0;i<listP.size();i++){
        	//查询参与过游戏的所有人的最高分数
        	mapU = saveLoginShareLogDao.getMaxNumOfDayBypeople(listP.get(i).get("open_id").toString());
        	list.add(mapU);
        }
        if(list!=null){
        	for(int i=0;i<list.size();i++){
        		lasttime = list.get(i).get("createtime").toString();
        		game_num = Integer.parseInt(list.get(i).get("game_num").toString());
        		open_id = list.get(i).get("open_id").toString();
        		nick_name = list.get(i).get("nick_name").toString();
        		head_imgurl = list.get(i).get("head_imgurl").toString();
        		logger.info("lasttime="+lasttime+"  game_num="+game_num+"   open_id="+open_id+" nick_name="+nick_name+" head_imgurl="+head_imgurl );
        		if(saveLoginShareLogDao.saveMaxNumOfDay(lasttime, game_num, open_id, nick_name, head_imgurl)>0){
        			logger.info("自动添加每天每人游戏的最高分成功！");
        		}else{
        			logger.info("自动添加每天每人游戏的最高分失败！");
        		}
        	}
        }
        map = saveLoginShareLogDao.getGameMaxNum();
        if(map!=null){
        	int i=0;
        	i = saveLoginShareLogDao.saveGameMaxNum(map.get("lasttime").toString(),map.get("open_id").toString(),
        			map.get("nick_name").toString(), map.get("head_imgurl").toString(), Integer.parseInt(map.get("game_num").toString()));
        	if(i>0){
        		logger.info("自动添加历史最高分成功！");
        	}else{
        		logger.info("自动添加历史最高分失败！");
        	}
        }else{
        	logger.info("未找到最高分信息！");
        }
        Long s2 = System.currentTimeMillis();
        logger.info("AnalysisTask:"+(s2-s)+"毫秒");
    }
    public static void main(String[] args) {
    	/*SaveLoginShareLogDao saveLoginShareLogDao=new SaveLoginShareLogDao();
    	System.out.println(saveLoginShareLogDao.getPeople());
    	List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
    	Map<String, Object> mapU = new HashMap<String, Object>();
    	List<Map<String, Object>> listP=new ArrayList<Map<String,Object>>();
    	//查询昨天参与过游戏的所有人
        listP = saveLoginShareLogDao.getPeople();
    	 for(int i=0;i<listP.size();i++){
         	//查询参与过游戏的所有人的最高分数
         	mapU = saveLoginShareLogDao.getMaxNumOfDayBypeople(listP.get(i).get("open_id").toString());
         	System.out.println(mapU);
         	list.add(mapU);
         }
    	 for(int i=0;i<list.size();i++){
    		 System.out.println(list.get(i).get("nick_name")+":"+list.get(i).get("game_num"));
    	 }
    	System.out.println(saveLoginShareLogDao.getGameMaxNum());*/
	}
}
