package com.jin.admin.util;


import com.jin.admin.common.constant.TypeEnum;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.commons.lang3.time.DateUtils.parseDate;

/**
 * 时间工具类 
 */
public class DateUtil {

	//默认显示日期的格式
	public static final String DATAFORMAT_STR = "yyyy-MM-dd";
	
	public static final String DATAFORMAT_HM_STR = "yyyy-MM-dd HH:mm";
	
	public static final String DATAFORMAT_LOCAL_STR = "yyyy年M月d日 H时m分";
	
	//默认显示日期的格式
	public static final String DATAFORMAT_FULL_STR = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DATAFORMAT_OSS_STR = "yyyy-MM-dd-HH-mm-ss";
	
	public static final String DATAFORMAT_LOCAL_FULL_STR = "yyyy/MM/dd HH:mm:ss";
	
	public static final String DATAFORMAT_LOCAL_POINT_STR = "yyyy.MM.dd HH:mm:ss";

	/**
	 * 当天零点毫秒数
	 */
	public static final Integer CURRENT_ZERO_TIMESTAMP = 1;
	/**
	 * 当天23点59分59秒的毫秒数
	 */
	public static final Integer CURRENT_LAST_TIMESTAMP = 2;
	/**
	 * 昨天这个时间的毫秒数
	 */
	public static final Integer CURRENT_YESTERDAY_TIMESTAMP = 3;

	private static String[] parsePatterns = {
			"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
			"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};
		
    /**
     * 时间字符串转换成Date类型
    * @param dateStr
    * @param format
    * @return
     */
    public static Date dateStrToDate(String dateStr,String format){
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
    	try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
    } 
    public static Date yyyyMMddToDate(String dateStr) {
    	if (ObjectUtils.isEmpty(dateStr)) {
    		return null;
		}
    	return dateStrToDate(dateStr, DATAFORMAT_STR);
	}

	public static Date str2Date(String dateStr) {
		if (ObjectUtils.isEmpty(dateStr)) {
			return null;
		}
		return dateStrToDate(dateStr, DATAFORMAT_FULL_STR);
	}
    public static String dateTodateStr(Date date,String format){
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
    	try {
    		if(date==null){//如果date为空，返回null
    			return null;
    		}
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    } 
    
    /**
     * 判断选择的日期是否是本周
     * @param time
     * @return
     */
    public static boolean isThisWeek(Date time)
    {
    	Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(time);
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        if(paramWeek==currentWeek){
     	   return true;
        }
        return false;
    }
    
    /**
     * 判断date2-date1相差天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
       int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年            
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            
            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }
    
    /**
    * 得到本周周一
    * 
    * @return date
    */
    public static Date getMondayOfThisWeek() {
	    Calendar c = Calendar.getInstance();
	    c.set(c.get(Calendar.YEAR), c.get(Calendar.MONDAY), c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
	    int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
	    if (day_of_week == 0) {
	    	day_of_week = 7;
	    }
	    c.add(Calendar.DATE, -day_of_week + 1);
	    return c.getTime();
    }

    /**
    * 得到本周周日
    * 
    * @return date
    */
    public static Date getSundayOfThisWeek() {
	    Calendar c = Calendar.getInstance();
	    int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
	    if (day_of_week == 0) {
	    	day_of_week = 7;
	    }
	    c.add(Calendar.DATE, -day_of_week + 7);
	    return c.getTime();
    }
    
    /**
     * 获得本周一0点时间
     * @return
     */
  	public static Date getTimesWeekmorning() {
  		Calendar cal = Calendar.getInstance();
  		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
  		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
  		return  cal.getTime();
  	}
   
  	/**
  	 * 获得本周日24点时间
  	 * @return
  	 */
  	public  static Date getTimesWeeknight() {
  		Calendar cal = Calendar.getInstance();
  		cal.setTime(getTimesWeekmorning());
  		cal.add(Calendar.DAY_OF_WEEK, 7);
  		return cal.getTime();
  	}
  	
 	/**
 	 * 获得本月第一天0点时间
 	 * @return
 	 */
 	public static Date getTimesMonthmorning() {
 		Calendar cal = Calendar.getInstance();
 		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
 		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
 		return  cal.getTime();
 	}
  
 	/**
 	 * 获得本月最后一天24点时间
 	 * @return
 	 */
 	public static Date getTimesMonthnight() {
 		Calendar cal = Calendar.getInstance();
 		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
 		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
 		cal.set(Calendar.HOUR_OF_DAY, 24);
 		return cal.getTime();
 	}

	/**
	 *
	 * @param cprq
	 * @return
	 */
	public static Date convertCnDate(String cprq) {
		int yearPos = cprq.indexOf("年");
		int monthPos = cprq.indexOf("月");
		String cnYear = cprq.substring(0, yearPos);
		String cnMonth = cprq.substring(yearPos + 1, monthPos);
		String cnDay = cprq.substring(monthPos + 1, cprq.length() - 1);

		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.parseInt(cnYear));
		c.set(Calendar.MONTH, Integer.parseInt(cnMonth)-1);
		c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(cnDay));
		return c.getTime();
	}


 	/**
 	 * Unix时间戳转换成Date类型
 	 * @param timestampString
 	 * @return
 	 */
 	public static Date TimeStampToDate(String timestampString){  
 		  Long timestamp = Long.parseLong(timestampString)*1000;  
 		  return new Date(timestamp);  
 	}
 	
 	/**
     * Unix时间戳转换为日期时间格式字符串
     */
    public static String TimeStampToDateString(String timestampString,String format) {
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
    	Long timestamp = Long.parseLong(timestampString)*1000;  
        return sdf.format(timestamp);
    }
    
    /**
 	 * 通过身份证获取出生年月日
 	 * @param idCard
 	 * @return
 	 */
	public static String getBirthdayByIdCard(String idCard) { 
	     String idCardNumber = idCard.trim(); 
	     int idCardLength = idCardNumber.length(); 
	     String birthday = null; 
	     if (idCardNumber == null || "".equals(idCardNumber)) { 
	         return null; 
	     } 
	     if (idCardLength == 18) { 
	         birthday = idCardNumber.substring(6, 10) + "-" 
	                 + idCardNumber.substring(10, 12) + "-" 
	                 + idCardNumber.substring(12, 14); 
	     } 
	     if (idCardLength == 15) { 
	         birthday = "19" + idCardNumber.substring(6, 8) + "-" 
	                 + idCardNumber.substring(8, 10) + "-" 
	                 + idCardNumber.substring(10, 12); 
	     } 
	     return birthday; 
	}

	/**
	 * 判断日期是否当天
	 * @param time
	 * @return
	 */
	public static boolean isToday(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATAFORMAT_STR);
		if (sdf.format(new Date()).equals(sdf.format(time))) {
			return true;
		}
		return false;
	}
	/**
	 * @author Jin
	 * @description 获取time的开始时刻
	 * @param time 时间
	 * @return java.util.Date
	 */
	public static Date getBeginOfDay(Date time) {
		if (ObjectUtils.isEmpty(time)) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
				0, 0, 0);
		return calendar.getTime();
	}

	/**
	 * @author Jin
	 * @description 获取time的结束时刻
	 * @param time 时间
	 * @return java.util.Date
	 */
	public static Date getEndOfDay(Date time) {
		if (ObjectUtils.isEmpty(time)) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
				23, 59, 59);
		return calendar.getTime();
	}
	/**
	 * @author Jin
	 * @description 获取time时刻所在周的开始时刻
	 * @param time 时间
	 * @return java.util.Date
	 */
	public static Date getBeginOfWeek(Date time) {
		if (ObjectUtils.isEmpty(time)) {
			return null;
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(time);

		int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayofweek == 1) {
			dayofweek += 7;
		}
		calendar.add(Calendar.DATE, 2 - dayofweek);
		return getBeginOfDay(calendar.getTime());
	}
	/**
	 * @author Jin
	 * @description 获取time时刻所在周的结束时刻
	 * @param time 时间
	 * @return java.util.Date
	 */
	public static Date getEndOfWeek(Date time) {
		if (ObjectUtils.isEmpty(time)) {
			return null;
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(getBeginOfWeek(time));
		calendar.add(Calendar.DAY_OF_WEEK, 6);
		return getEndOfDay(calendar.getTime());
	}

	/**
	 * @author Jin
	 * @description 获取time时刻所在月的开始时刻
	 * @param time 时间
	 * @return java.util.Date
	 */
	public static Date getBeginOfMonth(Date time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return getBeginOfDay(calendar.getTime());
	}

	/**
	 * @author Jin
	 * @description 获取time时刻所在月的结束时刻
	 * @param time 时间
	 * @return java.util.Date
	 */
	public static Date getEndOfMonth(Date time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return getEndOfDay(cal.getTime());
	}

	/**
	 * @author Jin
	 * @description 获取time时刻所在年份的开始时刻
	 * @param time 时间
	 * @return java.util.Date
	 */
	public static Date getBeginOfYear(Date time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return getBeginOfDay(cal.getTime());
	}

	/**
	 * @author Jin
	 * @description 获取time时刻所在年份的结束时刻
	 * @param time 时间
	 * @return java.util.Date
	 */
	public static Date getEndOfYear(Date time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.set(Calendar.MONTH, 11);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return getEndOfDay(cal.getTime());
	}

    /**
     * @author Jin
     * @description 获取time时间n天前的开始时刻
     * @param time 时间
     * @param n 天数
     * @return java.util.Date
     */
	public static Date getDayBefore(Date time, Integer n) {
	    if (ObjectUtils.isEmpty(time) || ObjectUtils.isEmpty(n)) {
	        return time;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.DATE, 0 - n);
        return getBeginOfDay(calendar.getTime());
    }

	/**
	 * 日期型字符串转化为日期 格式
	 */
	public static Date parseStr2Date(Object str)
	{
		if (str == null)
		{
			return null;
		}
		try
		{
			return parseDate(str.toString(), parsePatterns);
		}
		catch (ParseException e)
		{
			return null;
		}
	}

	public static String parseDateToStr(final String format, final Date date)
	{
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 获取毫秒数
	 * @param type 1-当天零点 2-当天最后一秒 3-昨天当前时间
	 * @return
	 */
	public static long getCurrentDayZeroTimestamp(Integer type){
		long current=System.currentTimeMillis();//当前时间毫秒数
		long value = 0;
		switch (type){
			case 1:
				value=current/(1000*3600*24)*(1000*3600*24)- TimeZone.getDefault().getRawOffset();
				break;
			case 2:
				long zero=current/(1000*3600*24)*(1000*3600*24)- TimeZone.getDefault().getRawOffset();
				value=zero+24*60*60*1000-1;
				break;
			case 3:
				value=System.currentTimeMillis()-24*60*60*1000;
				break;
		}
		return value;
	}

	/**
	 * @param code      时间段类型  1今天 2过去三天 3过去一周 4过去一月 5过去一年，此值存在则startTime、endTime无效
	 * @param startTime 开始时间  yyyy-MM-dd
	 * @param endTime   结束时间  yyyy-MM-dd
	 * @return java.util.List<java.util.Date>  第0位开始时间  第1位结束时间
	 * @author Jin
	 * @description 获取开始时间和结束时间
	 */
	public static List<Date> getStartAndEndTime(Integer code, String startTime, String endTime) {
		Date start = null;
		Date end = null;
		Date now = new Date();
		if (!ObjectUtils.isEmpty(code) && code < 6 && code >= 0) {
			// 指定特殊时间段
			end = DateUtil.getEndOfDay(now);
			TypeEnum.TimeCode timeCode = EnumUtil.getEnum(TypeEnum.TimeCode.class, code);
			switch (timeCode) {
				case TODAY:
					start = DateUtil.getBeginOfDay(now);
					break;
				case LAST_THREE_DAY:
					start = DateUtil.getDayBefore(now, 3);
					break;
				case LAST_WEEK:
					start = DateUtil.getBeginOfWeek(now);
					break;
				case LAST_MONTH:
					start = DateUtil.getBeginOfMonth(now);
					break;
				case LAST_YEAR:
					start = DateUtil.getBeginOfYear(now);
					break;
				default:
			}
		} else {
			// 通过startTime和endTime指定时间
			start = DateUtil.getBeginOfDay(DateUtil.yyyyMMddToDate(startTime));
			end = DateUtil.getEndOfDay(DateUtil.yyyyMMddToDate(endTime));
		}
		return Arrays.asList(start, end);
	}

	public static Date getYearLater(Integer num){
		// 取时间
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		// 把日期往后增加一年.整数往后推,负数往前移动
		calendar.add(Calendar.YEAR, num);
		return calendar.getTime();
	}

	public static Date getYearLater(Date time, Integer num){
		// 取时间
		Date date = null;
		if (ObjectUtils.isEmpty(time)) {
			date = new Date();
		} else {
			date = time;
		}
		return getYearLater(date, num);
	}
 }

