package dev.majes.base.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * 日期工具类
 * @author majes
 * @date 12/11/17.
 */

public class DateUtils {
    private static SimpleDateFormat m = new SimpleDateFormat("MM", Locale.getDefault());
    private static SimpleDateFormat d = new SimpleDateFormat("dd", Locale.getDefault());
    private static SimpleDateFormat md = new SimpleDateFormat("MM-dd", Locale.getDefault());
    private static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static SimpleDateFormat ymdDot = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
    private static SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private static SimpleDateFormat ymdhmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
    private static SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    private static SimpleDateFormat hm = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private static SimpleDateFormat mdhm = new SimpleDateFormat("MM月dd日 HH:mm", Locale.getDefault());
    private static SimpleDateFormat mdhmLink = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());

    /**
     * 年月日[1994-05-06]
     *
     * @param timeInMills
     * @return
     */
    public static String getYmd(long timeInMills) {
        return ymd.format(new java.util.Date(timeInMills));
    }

    /**
     * 年月日[1994-05-06]
     *
     * @param timeInMills
     * @return
     */
    public static String getYmdDot(long timeInMills) {
        return ymdDot.format(new java.util.Date(timeInMills));
    }

    public static String getYmdhms(long timeInMills) {
        return ymdhms.format(new java.util.Date(timeInMills));
    }

    public static String getYmdhmsS(long timeInMills) {
        return ymdhmss.format(new java.util.Date(timeInMills));
    }

    public static String getYmdhm(long timeInMills) {
        return ymdhm.format(new java.util.Date(timeInMills));
    }

    public static String getHm(long timeInMills) {
        return hm.format(new java.util.Date(timeInMills));
    }

    public static String getMd(long timeInMills) {
        return md.format(new java.util.Date(timeInMills));
    }

    public static String getMdhm(long timeInMills) {
        return mdhm.format(new java.util.Date(timeInMills));
    }

    public static String getMdhmLink(long timeInMills) {
        return mdhmLink.format(new java.util.Date(timeInMills));
    }

    public static String getM(long timeInMills) {
        return m.format(new java.util.Date(timeInMills));
    }

    public static String getD(long timeInMills) {
        return d.format(new java.util.Date(timeInMills));
    }

    /**
     * 是否是今天
     *
     * @param timeInMills
     * @return
     */
    public static boolean isToday(long timeInMills) {
        String dest = getYmd(timeInMills);
        String now = getYmd(Calendar.getInstance().getTimeInMillis());
        return dest.equals(now);
    }

    /**
     * 是否是同一天
     *
     * @param aMills
     * @param bMills
     * @return
     */
    public static boolean isSameDay(long aMills, long bMills) {
        String aDay = getYmd(aMills);
        String bDay = getYmd(bMills);
        return aDay.equals(bDay);
    }

    /**
     * 获取年份
     *
     * @param mills
     * @return
     */
    public static int getYear(long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取月份
     *
     * @param mills
     * @return
     */
    public static int getMonth(long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        return calendar.get(Calendar.MONTH) + 1;
    }


    /**
     * 获取月份的天数
     *
     * @param mills
     * @return
     */
    public static int getDaysInMonth(long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        switch (month) {
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                return 31;
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                return 30;
            case Calendar.FEBRUARY:
                return (year % 4 == 0) ? 29 : 28;
            default:
                throw new IllegalArgumentException("Invalid Month");
        }
    }


    /**
     * 获取星期,0-周日,1-周一，2-周二，3-周三，4-周四，5-周五，6-周六
     *
     * @param mills
     * @return
     */
    public static int getWeek(long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);

        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 获取当月第一天的时间（毫秒值）
     *
     * @param mills
     * @return
     */
    public static long getFirstOfMonth(long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return calendar.getTimeInMillis();
    }

}
