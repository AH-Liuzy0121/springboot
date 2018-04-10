package com.liuzy.module.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
  * @className: DateUtil
  * @package: com.liuzy.module.utils
  * @describe: 关于时间转换的工具类
  * @auther: liuzhiyong
  * @date: 2018/4/10
  * @time: 下午 2:13
  */
public class DateUtil {

    private static final String DATE_FORMAT_PATTERN_0= "yyyyMMdd";
    private static final String DATE_FORMAT_PATTERN_1= "yyyy/MM/dd";
    private static final String DATE_FORMAT_PATTERN_2= "yyyy-MM-dd";
    private static final String DATE_FORMAT_PATTERN_3= "yyyy-MM-dd HH:mm:ss";

    //将时间跟当前线程绑定
    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();

    private static SimpleDateFormat getDateFormat(String pattern) {
        SimpleDateFormat format = threadLocal.get();
        if (format == null || !pattern.equals(format.toPattern())) {
            format = new SimpleDateFormat(pattern);
            threadLocal.set(format);
        }
        return format;
    }

    /**
     * @methodName: getCurrentDatePatternZero
     * @param:
     * @describe: 获取当前日期
     *     Date to yyyyMMdd
     * @auther: liuzhiyong
     * @date: 2018/4/10
     * @time: 下午 2:31
     */
    public static String getCurrentDatePatternZero()throws Exception{
        return getDateFormat(DATE_FORMAT_PATTERN_0).format(new Date());
    }

    /**
     * @methodName: getCurrentDatePatternOne
     * @param:
     * @describe: 获取当前日期
     *     Date to yyyy/MM/dd
     * @auther: liuzhiyong
     * @date: 2018/4/10
     * @time: 下午 2:31
     */
    public static String getCurrentDatePatternOne()throws Exception{
        return getDateFormat(DATE_FORMAT_PATTERN_1).format(new Date());
    }

    /**
     * @methodName: getCurrentDatePatternTwo
     * @param:
     * @describe: 获取当前日期
     *     Date to yyyy-MM-dd
     * @auther: liuzhiyong
     * @date: 2018/4/10
     * @time: 下午 2:31
     */
    public static String getCurrentDatePatternTwo()throws Exception{
        return getDateFormat(DATE_FORMAT_PATTERN_2).format(new Date());
    }

    /**
     * @methodName: getCurrentTime
     * @param:
     * @describe: 获取当前时间
     *     Date to yyyy-MM-dd HH:mm:ss
     * @auther: liuzhiyong
     * @date: 2018/4/10
     * @time: 下午 2:31
     */
    public static String getCurrentTime()throws Exception{
        return getDateFormat(DATE_FORMAT_PATTERN_3).format(new Date());
    }

    /**
     * @methodName: getDateToString
     * @param: pattern  日期格式 date 指定日期
     * @describe: 将日期装换成指定的字符串格式 DateToString
     * @auther: liuzhiyong
     * @date: 2018/4/10
     * @time: 下午 2:38
     */
    public static String getDateToString(String pattern,Date date) throws Exception{
        return getDateFormat(pattern).format(date);
    }

    /**
     * @methodName: getTimestampToString
     * @param: pattern 日期格式 timestamp 指定时间
     * @describe: TimestampToString
     * @auther: liuzhiyong
     * @date: 2018/4/10
     * @time: 下午 2:41
     */
    public static String getTimestampToString(String pattern,Timestamp timestamp)throws Exception{
        return getDateFormat(pattern).format(timestamp);
    }

    /**
     * @methodName: getStringToDate
     * @param: pattern 日期格式 strDate 日期类的字符串
     * @describe: StringToDate
     * @auther: liuzhiyong
     * @date: 2018/4/10
     * @time: 下午 2:47
     */
    public static Date getStringToDate(String pattern,String strDate)throws Exception{
        return strDate == null ? null : getDateFormat(pattern).parse(strDate);
    }

   /**
    * @methodName: addByDaysTheDate
    * @param: date 指定日期 value (-2前天 -1昨天 0今天 1明天 2后天)
    * @describe: 计算指定日期之前的指定日期
    * @auther: liuzhiyong
    * @date: 2018/4/10
    * @time: 下午 2:50
    */
    public static Date addByDaysTheDate(Date date,int value){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);//设置日历值
        calendar.add(Calendar.DATE, value);
        return calendar.getTime();
    }

    /**
     * @methodName: getDayOfMonth
     * @param: date
     * @describe: 计算指定日期是当月的第几日
     * @auther: liuzhiyong
     * @date: 2018/4/10
     * @time: 下午 2:52
     */
    public static int getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * @methodName: getTheDayOfLastMonth
     * @param: date, dateFormat
     * @describe: 计算指定日期的上一个月的当天
     * @auther: liuzhiyong
     * @date: 2018/4/10
     * @time: 下午 2:52
     */
    public static String getTheDayOfLastMonth(Date date, String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat); //格式化对象
        Calendar calendar = Calendar.getInstance();	//日历对象
        calendar.setTime(date);		//设置当前日期
        calendar.add(Calendar.MONTH, -1);	//月份减一
        return sdf.format(calendar.getTime());
    }

   /**
    * @methodName: getDayOfLastMonth
    * @param: date
    * @describe: 计算指定日期的上一个月的当天
    * @auther: liuzhiyong
    * @date: 2018/4/10
    * @time: 下午 2:54
    */
    public static Date getDayOfLastMonth(Date date) {
        Calendar calendar = Calendar.getInstance(); // 日历对象
        calendar.setTime(date); // 设置当前日期
        calendar.add(Calendar.MONTH, -1); // 月份减一
        return calendar.getTime();
    }

    /**
     * @methodName: getTimeStampByDate
     * @param: date
     * @describe: 将日期格式yyyy-mm-dd转换为TimeStamp格式，时分秒为当前时分秒
     * @auther: liuzhiyong
     * @date: 2018/4/10
     * @time: 下午 2:55
     */
    public static Timestamp getTimeStampByDate(Date date){
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        Calendar cal2 = Calendar.getInstance();
        cal2.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), cal1.get(Calendar.DATE));
        return new Timestamp(cal2.getTime().getTime());
    }
}
