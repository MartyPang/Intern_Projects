package Thread;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sinocontact.common.DateUtils;
import com.sinocontact.common.PropertyUtils;

public class SaveGameMaxNum extends HttpServlet {
	private  static  final Logger logger = Logger.getLogger(SaveGameMaxNum.class);
	public SaveGameMaxNum() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
	@Override
	public void init() throws ServletException {
		 logger.info(" 线程开始启动------------------");
		 String sHour = PropertyUtils.getProperty("hour");
	        String sMinute = PropertyUtils.getProperty("minute");
	        String sSecond = PropertyUtils.getProperty("second");
	        String sPeriod = PropertyUtils.getProperty("period");


	        int hour = StringUtils.isEmpty(sHour) ? 1 : Integer.valueOf(sHour);
	        int minute = StringUtils.isEmpty(sMinute) ? 0 : Integer
	                .valueOf(sMinute);
	        int second = StringUtils.isEmpty(sSecond) ? 0 : Integer
	                .valueOf(sSecond);
	        int period = StringUtils.isEmpty(sPeriod) ? 1 : Integer
	                .valueOf(sPeriod);

	        Calendar calender = Calendar.getInstance();
	        calender.set(Calendar.HOUR_OF_DAY, hour);
	        calender.set(Calendar.MINUTE, minute);
	        calender.set(Calendar.SECOND, second);

	        Date firstTime = calender.getTime();

	        if (firstTime.before(new Date()))
	            firstTime = DateUtils.addDays(firstTime, period);

	        Timer UpdatePushEffectTimer = new Timer();
	        AnalysisTask analysisTask = new AnalysisTask();
	        UpdatePushEffectTimer.schedule(analysisTask, firstTime, period * 86400000);
	        logger.info("bubblesNumThread:"+firstTime);
	}

}
