package com.cassey.house.common.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by tangcheng on 2017/5/8.
 */
public class DateUtil {
    private static Pattern PATTERN_DURATION_BY_DAY = Pattern.compile("\\d+d", Pattern.CASE_INSENSITIVE);
    private static Pattern PATTERN_DURATION_BY_HOUR = Pattern.compile("\\d+h", Pattern.CASE_INSENSITIVE);
    private static Pattern PATTERN_DURATION_BY_MINUTE = Pattern.compile("\\d+m");

    public final static String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String DEFAULT_DATE_TIME_FORMAT_SHORT = "yyyyMMddHHmmss";
    public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public final static String SIMPLE_DATE_MONTH = "yyyyMM";
    public final static String SIMPLE_DATE_TIME_FORMAT = "yyyyMMdd HHmmss";
    public final static String DEFAULT_DATE_FORMAT_WITH_HOUR = "yyyy-MM-dd-HH";
    public final static String DEFAULT_DATE_FORMAT_WITH_HOUR_1 = "yyyy年MM月dd日 HH";
    public final static String DEFAULT_DATE_FORMAT_WITH_MINUTE = "yyyy年MM月dd日 HH:mm";
    public final static String DATE_FORMAT_WITH_DOT = "yyyy.MM.dd";
    public final static String DEFAULT_DATE_SIMPLE_FORMAT = "yyMMdd";
    public final static String SIMPLE_DATE_TIME = "HHmmss";
    public final static String DEFAULT_MONTH_FORMAT = "yyyy-MM";
    /**
     * 一分钟的毫秒数
     */
    public final static long MILLIS_OF_MINUTE = 60 * 1000;
    /**
     * 一小时的毫秒数
     */
    public final static long MILLIS_OF_HOUR = 60 * MILLIS_OF_MINUTE;
    /**
     * 一天的毫秒数
     */
    public final static long MILLIS_OF_DAY = 24 * MILLIS_OF_HOUR;

    /**
     * 一分钟的秒数
     */
    public final static long SECOND_OF_MINUTE = 60;
    /**
     * 半分钟的秒数
     */
    public final static long HALF_SECOND_OF_MINUTE = 30;
    /**
     * 一小时的秒数
     */
    public final static long SECOND_OF_HOUR = SECOND_OF_MINUTE * 60;
    /**
     * 一天的秒数
     */
    public final static long SECOND_OF_DAY = SECOND_OF_HOUR * 24;
    /**
     * 一周的秒数
     */
    public final static long SECOND_OF_WEEK = SECOND_OF_DAY * 7;
    /**
     * 一月的秒数
     */
    public final static long SECOND_OF_MONTH = SECOND_OF_DAY * 30;
    /**
     * 一年的秒数
     */
    public final static long SECOND_OF_YEAR = SECOND_OF_DAY * 365;

    /**
     * 获取m分钟的毫秒数
     *
     * @param m 分钟数
     * @return m * 60 * 1000
     */
    public static long getMillisOfMinutes(int m) {
        return MILLIS_OF_MINUTE * m;
    }

    /**
     * 获取h小时的毫秒数
     *
     * @param h 分钟数
     * @return h * 60 * 60 * 1000
     */
    public static long getMillisOfHours(int h) {
        return MILLIS_OF_HOUR * h;
    }

    /**
     * 获取d天的毫秒数
     *
     * @param d 分钟数
     * @return d * 24 * 60 * 60 * 1000
     */
    public static long getMillisOfDays(int d) {
        return MILLIS_OF_DAY * d;
    }

    /**
     * 10位时间戳转换为13位时间戳
     *
     * @param time
     * @return
     */
    public static long getMIllisOfSeconds(Integer time) {
        if (time == null) {
            return 0;
        }
        return (long) time * 1000;
    }

    public final static TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone("America/Los_Angeles");
    public static final String CHINA_TIMEZONE_ID = "GMT+08:00";


    public static Date add(Date date, long timeMillis) {
        return date == null ? null : new Date(date.getTime() + timeMillis);
    }

    public static int getYear(Date date) {
        if (null == date) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取毫秒数
     *
     * @param date
     * @return
     */
    public static long getMillis(Date date) {
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }

    public static String format(Date date) {
        return format(date, DEFAULT_DATE_TIME_FORMAT);
    }

    public static String format(Date date, String format) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static String format(Long date, String format) {
        if (date == null) {
            return null;
        }
        return format(new Date(date), format);
    }

    public static String formatByTimeZone(Long date, String format, String timeZone) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        return dateFormat.format(date);
    }

    /**
     * 获取时间间隔
     *
     * @param expr 时间间隔表达式如：5d(5天),3h(3小时),2m(2分钟)
     * @return
     */
    public static long getDuration(String expr) {
        if (StringUtils.isEmpty(expr)) {
            return Long.MAX_VALUE;
        } else if (PATTERN_DURATION_BY_HOUR.matcher(expr).matches()) {
            return Long.parseLong(expr.substring(0, expr.length() - 1)) * MILLIS_OF_HOUR;
        } else if (PATTERN_DURATION_BY_DAY.matcher(expr).matches()) {
            return Long.parseLong(expr.substring(0, expr.length() - 1)) * MILLIS_OF_DAY;
        } else if (PATTERN_DURATION_BY_MINUTE.matcher(expr).matches()) {
            return Long.parseLong(expr.substring(0, expr.length() - 1)) * MILLIS_OF_MINUTE;
        } else {
            return Long.MAX_VALUE;
        }
    }

    /**
     * 获取今天0点的时间戳，使用系统默认时区
     *
     * @return
     */
    public static long getBeginOfToday() {
        return getBeginOfToday(null);
    }

    /**
     * 获取指定时区今天默认的时间戳
     *
     * @param timeZone
     * @return
     */
    public static long getBeginOfToday(TimeZone timeZone) {
        return getBeginOfDay(System.currentTimeMillis(), timeZone);
    }

    public static long getBeginOfDay(Date date) {
        return getBeginOfDay(date, null);
    }

    public static long getBeginOfDay(Date date, TimeZone timeZone) {
        if (date == null) {
            return 0;
        }
        return getBeginOfDay(date.getTime(), timeZone);
    }

    public static long getBeginOfDay(Long timeInMillis) {
        return getBeginOfDay(timeInMillis, null);
    }

    public static long getEndOfDay(Long timeInMillis) {
        return getEndOfDay(timeInMillis, null);
    }

    public static long getEndOfDay(Long timeInMillis, TimeZone timeZone) {
        if (timeInMillis == null) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        if (timeZone != null) {
            calendar.setTimeZone(timeZone);
        }
        calendar.setTimeInMillis(timeInMillis);
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis() - 1;
    }

    public static long getBeginOfDay(Long timeInMillis, TimeZone timeZone) {
        if (timeInMillis == null) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        if (timeZone != null) {
            calendar.setTimeZone(timeZone);
        }
        calendar.setTimeInMillis(timeInMillis);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getBeginOfMonth(int year, int month) {
        return getBeginOfMonth(year, month, null);
    }

    public static long getBeginOfMonth(int year, int month, TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (timeZone != null) {
            calendar.setTimeZone(timeZone);
        }
        return calendar.getTimeInMillis();
    }

    public static long getBeginOfYear(int year) {
        return getBeginOfYear(year, null);
    }

    public static long getBeginOfYear(int year, TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, 0, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (timeZone != null) {
            calendar.setTimeZone(timeZone);
        }
        return calendar.getTimeInMillis();
    }

    /**
     * 根据毫秒获取时间字符串
     *
     * @param millis
     * @param formatString
     * @return
     */
    public static String getDateStringByMillis(Long millis, String formatString) {
        if (formatString == null) {
            formatString = DEFAULT_DATE_FORMAT;
        }
        // 获取当前月份
        SimpleDateFormat dft = new SimpleDateFormat(formatString);
        Calendar cal = Calendar.getInstance();
        if (isThirteenTimeStamp(millis)) {
            cal.setTimeInMillis(millis);
        }
        return dft.format(cal.getTime());
    }

    public static String getDateStringByMillis(Long millis, String formatString, TimeZone timeZone) {
        if (formatString == null) {
            formatString = DEFAULT_DATE_FORMAT;
        }
        // 获取当前月份
        SimpleDateFormat dft = new SimpleDateFormat(formatString);
        Calendar cal = Calendar.getInstance();
        if (isThirteenTimeStamp(millis)) {
            cal.setTimeInMillis(millis);
        }
        if (timeZone != null) {
            dft.setTimeZone(timeZone);
        }
        return dft.format(cal.getTime());
    }

    /**
     * 判断是否13位时间戳
     *
     * @param timeStamp
     * @return
     */
    public static boolean isThirteenTimeStamp(Long timeStamp) {
        if (timeStamp == null) {
            return false;
        }
        if (String.valueOf(timeStamp).length() != 13) {
            return false;
        }
        return true;
    }

    /**
     * 获取日期，失败返回null
     *
     * @param date
     * @param format
     * @return
     */
    public static Date getDate(String date, String format) {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat(format).parse(date);
        } catch (Exception e) {

        }
        return date1;
    }

    /**
     * 获取日期，失败返回null
     *
     * @param date
     * @param format
     * @return
     */
    public static Date getDate(String date, String format, TimeZone timeZone) {
        Date date1 = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            if (timeZone != null) {
                sdf.setTimeZone(timeZone);
            }
            date1 = sdf.parse(date);
        } catch (Exception e) {

        }
        return date1;
    }

    public static int getTimestampBySecond() {
        return getTimestampBySecond(System.currentTimeMillis());
    }

    public static int getTimestampBySecond(long timestamp) {
        return (int) (timestamp / 1000);
    }

    public static Integer getTimestampBySecond(Date date) {
        return date == null ? null : getTimestampBySecond(date.getTime());
    }

    /**
     * 判断当前时间是否为夏令时
     *
     * @return
     */
    public static boolean isSummer() {
        long zeroTimeMills = getBeginOfDay(System.currentTimeMillis(), DEFAULT_TIME_ZONE);
        if ("150000".equals(DateUtil.getDateStringByMillis(zeroTimeMills + DateUtil.MILLIS_OF_DAY, DateUtil.SIMPLE_DATE_TIME, TimeZone.getTimeZone(CHINA_TIMEZONE_ID)))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据时间戳获取默认时区的日期
     *
     * @param millis
     * @return
     */
    public static Date getDateFromMillis(long millis) {
        Calendar cal = Calendar.getInstance();
        if (isThirteenTimeStamp(millis)) {
            cal.setTimeInMillis(millis);
        }
        return cal.getTime();

    }

    /**
     * 获取星期几数字
     *
     * @param zoneId 时区
     * @return
     */
    public static int getDayOfTheWeek(String zoneId) {
        LocalDate currentDate = LocalDate.now(ZoneId.of(zoneId));
        return currentDate.getDayOfWeek().getValue();
    }

    public static String getDateStringByMillisIgnoreThirteenTime(Long millis, String formatString, TimeZone timeZone) {
        if (formatString == null) {
            formatString = DEFAULT_DATE_FORMAT;
        }
        // 获取当前月份
        SimpleDateFormat dft = new SimpleDateFormat(formatString);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);

        if (timeZone != null) {
            dft.setTimeZone(timeZone);
        }
        return dft.format(cal.getTime());
    }

    /**
     * 设置时间描述
     *
     * @param time
     * @return
     */
    public static String computeTimeDesc(Long time) {
        if (null == time || 0 == time) {
            return null;
        }
        long dValue = (System.currentTimeMillis() - time) / 1000;
        StringBuilder result = new StringBuilder();
        if (dValue < HALF_SECOND_OF_MINUTE) {
            return result.append((int) dValue).append("秒前").toString();
        } else if (HALF_SECOND_OF_MINUTE <= dValue && dValue < SECOND_OF_MINUTE) {
            return result.append("半分钟前").toString();
        } else if (SECOND_OF_MINUTE <= dValue && dValue < SECOND_OF_HOUR) {
            return result.append((int) (dValue / SECOND_OF_MINUTE)).append("分钟前").toString();
        } else if (SECOND_OF_HOUR <= dValue && dValue < SECOND_OF_DAY) {
            return result.append((int) (dValue / SECOND_OF_HOUR)).append("小时前").toString();
        } else if (SECOND_OF_DAY <= dValue && dValue < SECOND_OF_WEEK) {
            return result.append((int) (dValue / SECOND_OF_DAY)).append("天前").toString();
        } else if (SECOND_OF_WEEK <= dValue && dValue < SECOND_OF_MONTH) {
            return result.append((int) (dValue / SECOND_OF_WEEK)).append("星期前").toString();
        } else if (SECOND_OF_MONTH <= dValue && dValue < SECOND_OF_YEAR) {
            return result.append((int) (dValue / SECOND_OF_MONTH)).append("月前").toString();
        } else if (SECOND_OF_YEAR <= dValue) {
            return result.append((int) (dValue / SECOND_OF_YEAR)).append("年前").toString();
        }
        return null;
    }


    /**
     * 计算两个日期之间的天数
     *
     * @param timeStart
     * @param timeEnd
     * @return
     */
    public static int daysOfTwoDate(long timeStart, long timeEnd) {
        return Math.round((timeEnd - timeStart) / MILLIS_OF_DAY);
    }

    /**
     * 获取已经结算的时间戳
     *
     * @param millis
     * @return
     */
    public static long[] getSettledTimeScope(Long millis, String period, TimeZone timeZone) {
        List<Integer> days = new ArrayList<>();
        String[] split = period.split(",");
        for (String s : split) {
            days.add(Integer.parseInt(s));
        }

        long[] timeScope = new long[2];
        // 获取当前月份
        SimpleDateFormat dft = new SimpleDateFormat(SIMPLE_DATE_MONTH);
        Calendar cal = Calendar.getInstance();

        if (millis != null) {
            cal.setTimeInMillis(millis);
        }
        // 获取当前的号数
        int day = cal.get(Calendar.DAY_OF_MONTH);
        // 当前月份
        String nowMonth = dft.format(cal.getTime());
        cal.add(Calendar.MONTH, -1);
        // 上个月份
        String lastMonth = dft.format(cal.getTime());
        int x = days.indexOf(day);
        // 此时不存在
        if (x < 0) {
            return null;
        }
        String timeSuffix = " 000000";
        String time0;
        String time1;
        int t0;
        if (day < 10) {
            time1 = "0" + day + timeSuffix;
        } else {
            time1 = day + timeSuffix;
        }
        if (x == 0) {
            t0 = days.get(days.size() - 1);
            if (t0 < 10) {
                time0 = "0" + t0 + timeSuffix;
            } else {
                time0 = t0 + timeSuffix;
            }
            timeScope[0] = getMillis(getDate(lastMonth + time0, SIMPLE_DATE_TIME_FORMAT, timeZone));
        } else {
            t0 = days.get(x - 1);
            if (t0 < 10) {
                time0 = "0" + t0 + timeSuffix;
            } else {
                time0 = t0 + timeSuffix;
            }
            timeScope[0] = getMillis(getDate(nowMonth + time0, SIMPLE_DATE_TIME_FORMAT, timeZone));
        }
        timeScope[1] = getMillis(getDate(nowMonth + time1, SIMPLE_DATE_TIME_FORMAT, timeZone));
        return timeScope;
    }

    /**
     * 获取今天0点的时间戳
     *
     * @return
     */
    public static long getTodayZeroMillis(long millis) {
        Date date = getDateFromMillis(millis);
        getDateStringByMillis(date.getTime(), null);
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return getMillis(getDate(sdf.format(date), DEFAULT_DATE_FORMAT));

    }

    /**
     * 获取今天0点的时间戳
     *
     * @return
     */
    public static long getTodayZeroMillis(long millis, TimeZone timeZone) {
        Date date = getDateFromMillis(millis);
        getDateStringByMillis(date.getTime(), null);
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        if (timeZone != null) {
            sdf.setTimeZone(timeZone);
        }
        return getMillis(getDate(sdf.format(date), DEFAULT_DATE_FORMAT, timeZone));

    }

    /**
     * 获取当前结算的周期范围 商家app 结算管理
     *
     * @param millis
     * @return
     */
    public static Long[] getCurrentSettleTimeScope(Long millis, String period, TimeZone timeZone) {

        List<Integer> days = new ArrayList<>();
        String[] split = period.split(",");
        for (String s : split) {
            days.add(Integer.parseInt(s));
        }
        Long[] timeScope = new Long[2];
        // 获取当前月份
        SimpleDateFormat dft = new SimpleDateFormat(SIMPLE_DATE_MONTH);
        Calendar cal = Calendar.getInstance();
        if (millis != null) {
            cal.setTimeInMillis(millis);
        }
        if (timeZone != null) {
            cal.setTimeZone(timeZone);
        }
        // 获取当前的号数
        int day = cal.get(Calendar.DAY_OF_MONTH);
        // 当前月份
        String nowMonth = dft.format(cal.getTime());
        cal.add(Calendar.MONTH, +1);
        // 下个月份
        String nextMonth = dft.format(cal.getTime());

        cal.add(Calendar.MONTH, -2);
        // 上个月份
        String lastMonth = dft.format(cal.getTime());

        String timeSuffix = " 000000";
        String time0 = "";
        String time1 = "";

        String dayStr;
        String dayStr1;
        // 3 8
        for (int i = 0; i < days.size(); i++) {
            int start = i;
            int end;
            int indexEnd = days.size() - 1;
            if (i == indexEnd) {
                end = 0;
            } else {
                end = start + 1;
            }

            // 左边
            if (i == 0) {
                Integer tempDay = days.get(indexEnd);
                Integer tempDay1 = days.get(i);
                // 比如今天3号，结算周期是8-3号不包括3号的
                if (day < tempDay1) {
                    if (tempDay < 10) {
                        dayStr = "0" + tempDay;
                    } else {
                        dayStr = "" + tempDay;
                    }
                    if (tempDay1 < 10) {
                        dayStr1 = "0" + tempDay1;
                    } else {
                        dayStr1 = "" + tempDay1;
                    }
                    time0 = lastMonth + dayStr + timeSuffix;
                    time1 = nowMonth + dayStr1 + timeSuffix;
                    break;
                }
            }

            // 右边范围
            if (i == indexEnd) {
                Integer tempDay = days.get(0);
                Integer tempDay1 = days.get(i);
                if (day >= tempDay1) {
                    if (tempDay < 10) {
                        dayStr = "0" + tempDay;
                    } else {
                        dayStr = tempDay + "";
                    }
                    if (tempDay1 < 10) {
                        dayStr1 = "0" + tempDay1;
                    } else {
                        dayStr1 = tempDay1 + "";
                    }
                    time0 = nowMonth + dayStr1 + timeSuffix;
                    time1 = nextMonth + dayStr + timeSuffix;
                    break;
                }
            }

            // 中间的
            if (day >= days.get(start) && day < days.get(end)) {
                Integer startDay = days.get(start);
                if (startDay < 10) {
                    dayStr = "0" + startDay;
                } else {
                    dayStr = startDay + "";
                }
                time0 = nowMonth + dayStr + timeSuffix;
                Integer endDay = days.get(end);
                if (endDay < 10) {
                    dayStr1 = "0" + endDay;
                } else {
                    dayStr1 = endDay + "";
                }
                time1 = nowMonth + dayStr1 + timeSuffix;
                break;
            }

        }
        timeScope[0] = getMillis(getDate(time0, SIMPLE_DATE_TIME_FORMAT, timeZone));
        timeScope[1] = getMillis(getDate(time1, SIMPLE_DATE_TIME_FORMAT, timeZone));
        return timeScope;
    }

    /**
     * 根据默认时区毫秒获取指定时区毫秒
     *
     * @param timeZone
     * @return
     */
    public static Long getMillsFromTimezone(TimeZone timeZone) {
        TimeZone aDefault = TimeZone.getDefault();
        TimeZone.setDefault(timeZone);
        Calendar cal = Calendar.getInstance();
        Long mills = cal.getTimeInMillis();
        // 获取当前时间字符串
        String dateStringByMillis = getDateStringByMillis(mills, DEFAULT_DATE_TIME_FORMAT);
        // 这个点其他时区的
        Date date = getDate(dateStringByMillis, DEFAULT_DATE_TIME_FORMAT, timeZone);
        // 还原默认时区
        TimeZone.setDefault(aDefault);
        return date.getTime();
    }

    /**
     * 根据时区获取当前是几号
     *
     * @param timeZone
     * @return
     */
    public static int getCurrentDay(TimeZone timeZone) {
        Long currMin = getBeginOfToday(timeZone);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currMin);
        if (timeZone != null) {
            calendar.setTimeZone(timeZone);
        }
        return calendar.get(Calendar.DAY_OF_MONTH);

    }

    /**
     * 整数秒转换成时分秒xx:xx:xx
     *
     * @param time
     * @return
     */
    public static String secToTime(int time) {
        String timeStr;
        int hour;
        int minute;
        int second;
        if (time <= 0)
            return "00:00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    /**
     * 两位数补0
     *
     * @param i
     * @return
     */
    public static String unitFormat(int i) {
        String retStr;
        if (i >= 0 && i < 10)
            retStr = "0" + i;
        else
            retStr = "" + i;
        return retStr;
    }

    public static String formatCost(final long cost) {
        return cost < 1000 ? (cost + "ms") : (cost == 1000 ? "1s" : new DecimalFormat("#.###s").format((double) cost / 1000));
    }
}
