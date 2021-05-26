package com.tilitili.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");

    /**
     * 计算天数
     * @param startTime ： 开始时间
     * @param endTime  ： 结束时间
     * @return
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

    public static String addDays(String dateTime,int n) throws ParseException {
        SimpleDateFormat sdfD = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdfD.parse(dateTime);
        Calendar calstart = Calendar.getInstance();
        calstart.setTime(date);
        calstart.add(Calendar.DAY_OF_WEEK, n);
        return sdfD.format(calstart.getTime());
    }

    public static Date addDay(Date dateTime,int n){
        SimpleDateFormat sdfD = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calstart = Calendar.getInstance();
        calstart.setTime(dateTime);
        calstart.add(Calendar.DAY_OF_WEEK, n);
        return calstart.getTime();
    }

    public static String addDayfmt(String dateTime,int n) throws ParseException {
        SimpleDateFormat sdfD = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = sdfD.parse(dateTime);
        Calendar calstart = Calendar.getInstance();
        calstart.setTime(date);
        calstart.add(Calendar.DAY_OF_WEEK, n);
        return sdf.format(calstart.getTime());
    }


    public static Date getCurrentDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date getNextDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    public static Date getLastDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }

    public static Date getT1() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }

    public static String getT1s() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DATE, -1);
        return sdfDay.format(c.getTime());
    }

    public static String getDateByYearAndMonth(String year, String month) {
        return year + "-" + (Integer.parseInt(month) < 10? "0": "") + month;
    }
}
