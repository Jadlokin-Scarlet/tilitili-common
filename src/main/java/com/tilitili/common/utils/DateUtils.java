package com.tilitili.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    /**
     * 日期转成字符串 格式为yyyy-MM-dd
     */
    public static String formatDateYMD(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        return sdfDay.format(date);
    }

    /**
     * 日期转换成字符串 格式为yyyy-MM-dd HH:mm:ss
     */
    public static String formatDateYMDHMS(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 线程安全
     * 日期转成字符串 格式为yyyy-MM-dd
     */
    public static Date parseDateYMD(String date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdfDay.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 线程安全
     * 日期转成字符串 格式为yyyy-MM-dd
     */
    public static Date parseDateYM(String date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM");
        try {
            return sdfDay.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 计算天数
     * @param startTime ： 开始时间
     * @param endTime  ： 结束时间
     */
    public static int caculateDay(String startTime,String endTime) {
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd");
        int l = 0;
        try {
            Date date = formatter.parse(startTime);
            Date date1 = formatter.parse(endTime);
            l = caculateDay(date, date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l;
    }

    public static int caculateDay(Date startTime,Date endTime) {
        long ts = startTime.getTime();
        long ts1 = endTime.getTime();

        long l = (ts - ts1) / (1000 * 60 * 60 * 24);

        return (int) l;
    }

    public static String addDays(Date dateTime,int n){
        SimpleDateFormat sdfD = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calstart = Calendar.getInstance();
        calstart.setTime(dateTime);
        calstart.add(Calendar.DAY_OF_WEEK, n);
        return sdfD.format(calstart.getTime());
    }

    public static String addDays(String dateTime,int n) {
        try {
            SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdfDay.parse(dateTime);
            Calendar calstart = Calendar.getInstance();
            calstart.setTime(date);
            calstart.add(Calendar.DAY_OF_WEEK, n);
            return sdfDay.format(calstart.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date addDay(Date dateTime,int n){
        SimpleDateFormat sdfD = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calstart = Calendar.getInstance();
        calstart.setTime(dateTime);
        calstart.add(Calendar.DAY_OF_WEEK, n);
        return calstart.getTime();
    }

    public static Date getCurrentDay() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date getLastDay() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }

    public static Date getNextDay() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    public static String getCurrentDays() {
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return sdfDay.format(c.getTime());
    }

    public static String getLastDays() {
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DATE, -1);
        return sdfDay.format(c.getTime());
    }

    public static String getNextDays() {
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DATE, 1);
        return sdfDay.format(c.getTime());
    }

    public static String getDateByYearAndMonth(String year, String month) {
        return year + "-" + (Integer.parseInt(month) < 10? "0": "") + month;
    }
}
