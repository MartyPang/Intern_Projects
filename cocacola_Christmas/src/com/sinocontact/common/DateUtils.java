package com.sinocontact.common;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public abstract class DateUtils
{
	private static final Logger logger=Logger.getLogger(DateUtils.class);
	
    // 日期格式，年份，例如：2004，2008
    public static final String DATE_FORMAT_YYYY = "yyyy";

    // 日期格式，年份和月份，例如：200707，200808
    public static final String DATE_FORMAT_YYYYMM = "yyyyMM";

    // 日期格式，年月日，例如：20050630，20080808
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";

    // 日期格式，年月日，用横杠分开，例如：2006-12-25，2008-08-08
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public static final String DATE_FORMAT_HH_MI_SS = "HH:mm:ss";

    // 日期格式，年月日时分秒，例如：20001230120000，20080808200808
    public static final String DATE_TIME_FORMAT_YYYYMMDDHHMISS = "yyyyMMddHHmmss";

    // 日期格式，年月日时分秒，年月日用横杠分开，时分秒用冒号分开，
    // 例如：2005-05-10 23：20：00，2008-08-08 20:08:08
    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS = "yyyy-MM-dd HH:mm:ss";

    private static SimpleDateFormat datetimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
    private static SimpleDateFormat timeFormat = new SimpleDateFormat(DATE_FORMAT_HH_MI_SS);

    public static Date parseDateTime(String dateStr)
	{
        Date reDate = null;

        try
        {
            reDate = datetimeFormat.parse(dateStr);
        } catch (ParseException e)
        {
            reDate = null;
        }
        return reDate;
	}


    public static Date parseDate(String dateStr)
    {
        Date reDate = null;

        try
        {
            reDate = dateFormat.parse(dateStr);
        } catch (ParseException e)
        {
            reDate = null;
        }
        return reDate;
    }

    public static Date parseDateTime(String dateStr,String parseStr)
    {
        Date reDate = null;

        try
        {
            SimpleDateFormat timeFormat = new SimpleDateFormat(parseStr);
            reDate = timeFormat.parse(dateStr);
        } catch (ParseException e)
        {
            reDate = null;
        }
        return reDate;
    }

	public static String formatDate(Date date)
	{
		DateFormat df = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
		return df.format(date);
	}

	public static String formatTime(Date date,String formatStr)
	{
		DateFormat df = new SimpleDateFormat(formatStr);
		return df.format(date);
	}
	
	public static String formatTime(Date date)
	{
		return datetimeFormat.format(date);
	}
	
	public static List<Date[]> splitTimeByDays(Date start, Date end, int days)
	{
		return splitTimeByHours(start, end, 24 * days);
	}

	public static List<Date[]> splitTimeByHours(Date start, Date end, int hours)
	{
		List<Date[]> dl = new ArrayList<Date[]>();
		while (start.compareTo(end) < 0)
		{
			Date _end = addHours(start, hours);
			if (_end.compareTo(end) > 0)
			{
				_end = end;
			}
			Date[] dates = new Date[]
			{ (Date) start.clone(), (Date) _end.clone() };
			dl.add(dates);

			start = _end;
		}
		return dl;
	}

	public static Date addMinutes(Date date, int amount)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, amount);
		return c.getTime();
	}

	public static Date addHours(Date date, int amount)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, amount);
		return c.getTime();
	}

	public static Date addDays(Date date, int amount)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, amount);
		return c.getTime();
	}

	public static String addDaysToStr(Date date, int amount)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, amount);
        DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
        return df.format(c.getTime());
	}

	public static String addDaysToStr(String dateStr, int amount)
	{
        Date date = parseDateTime(dateStr);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, amount);
        DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
        return df.format(c.getTime());
	}

	public static String addDaysToStr(Date date, int amount,String format)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, amount);
        DateFormat df = new SimpleDateFormat(format);
        return df.format(c.getTime());
	}


	public static Date addMonths(Date date, int amount)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, amount);
		return c.getTime();
	}

    public static String addMonthsToStr(Date date, int amount)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, amount);
        DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
        return df.format(c.getTime());
    }
    public static String addMonthsToStr(String dateStr, int amount)
    {
        Date date = parseDateTime(dateStr);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, amount);
        DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
        return df.format(c.getTime());
    }
    public static String addMonthsToStr(Date date, int amount,String format)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, amount);
        DateFormat df = new SimpleDateFormat(format);
        return df.format(c.getTime());
    }
    public static String addMonthsToDateStr(String dateStr, int amount)
    {
    	Date date = parseDate(dateStr);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, amount);
        DateFormat df = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
        return df.format(c.getTime());
    }

	/**
	 * 获取今天的开始时刻。
	 */
	public static Date getTodayStartTime()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

    /**
     * 获取今天的结束时间
     *
     */
    public static Date getTodayEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }
    /**
     * 获取当前日期（yyyy-MM-dd）
     *
     * @return 当前日期
     */
    public static String getCurrentDate()
    {
        return dateFormat.format(new Date());
    }

    /**
     * 获取当前时间（HH:mm:ss）
     *
     * @return 当前时间
     */
    public static String getCurrentTime()
    {
        return timeFormat.format(new Date());
    }

    /**
     * 获取当前日期与时间（yyyy-MM-dd HH:mm:ss）
     *
     * @return 当前日期与时间
     */
    public static String getCurrentDateTime()
    {
        return datetimeFormat.format(new Date());
    }

    /**
     *
     * @Title: dateToStr
     * @Description: 将时间类型转换为字符串
     * @param date
     * @param format
     * @return String
     */
    public static String dateToStr(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }


    public  static long dateDiff(Date start,Date stop){
        Long d = stop.getTime() - start.getTime();
        if (d%(1000*60*60*24)==0){
            return d/(1000*60*60*24);
        }else if(d%(1000*60*60*24) > (1000*60*60*12)){
            return d/(1000*60*60*24)+1;
        }else{
            return d/(1000*60*60*24);
        }
    }
    public  static long dateDiff(String startStr,String stopStr){
        Date start = parseDate(startStr);
        Date stop = parseDate(stopStr);
        Long d = stop.getTime() - start.getTime();
        if (d%(1000*60*60*24)==0){
            return d/(1000*60*60*24);
        }else if(d%(1000*60*60*24) > (1000*60*60*12)){
            return d/(1000*60*60*24)+1;
        }else{
            return d/(1000*60*60*24);
        }
    }
    
    public static String getFirstDayOfThisMouth(){
    	String firstDay="";
    	try{
    		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD); 
        	Calendar c = Calendar.getInstance();    
            c.add(Calendar.MONTH, 0);
            c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
            firstDay = format.format(c.getTime());
    	}catch(Exception e){
    		logger.error("",e);
    	}
        return firstDay;
    }
    
    public static void main(String[] args)
    {
        logger.info(getFirstDayOfThisMouth());
        logger.info(addMonthsToDateStr(getFirstDayOfThisMouth(), 1));
    }
}
