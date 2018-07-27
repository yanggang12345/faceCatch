package com.nala.faceCatch.util;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: heshangqiu
 * Created Date:  2018/3/14
 * 日期工具类
 * <p>
 * 统一采用JodaTime
 */
public class DateUtil {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_MSEC_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DATETIMESTR = "yyyyMMddHHmmssSSS";
    public static final String MINUTESTR = "mmssSSS";
    public static final String TIME_FORMAT = "HH:mm:ss";

    public static final String DATE_STAMP = "yyyyMMdd";
    public static final String DATETIME_STAMP = "yyyyMMddHHmmss";

    public static final String SUFFIX = " 00:00:00";

    public static final Long ONEDAYTIME = 24 * 60 * 60 * 1000L;

    public static final String DATE_FORMAT2 = "yyyy-MM";

    public static final String DATETIME_FORMAT_CH = "yyyy年MM月dd日HH:mm:ss";

    private static ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 返回当前时间
     *
     * @return date
     */
    public static Date now() {
        return DateTime.now().toDate();
    }

    /**
     * 返回时间字符串，以pattern为模板
     *
     * @param pattern 默认为 yyyy-MM-dd
     * @return
     */
    public static String toDateString(Date date, String pattern) {
        return new DateTime(date).toString(pattern);
    }

    /**
     * 返回时间字符串，以pattern为模板
     * <p>
     * pattern 默认为 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String toDateString(Date date) {
        if (date == null) {
            return null;
        }
        return toDateString(date, DATETIME_FORMAT);
    }

    /**
     * 将日期字符串解析为Date
     *
     * @param dateStr 日期字符串
     * @param pattern 日期匹配格式
     * @return
     */
    public static Date parse(String dateStr, String pattern) {
        return DateTime.parse(dateStr, DateTimeFormat.forPattern(pattern)).toDate();
    }

    /**
     * 将日期字符串解析为Date
     * <p>
     * pattern 默认为 yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr 日期字符串
     * @return
     */
    public static Date parse(String dateStr) {
        return parse(dateStr, DATETIME_FORMAT);
    }


    /**
     * 根据时间毫秒数转换为日期类型
     *
     * @param millis
     * @return
     */
    public static Date toDate(long millis) {
        return new DateTime(millis).toDate();
    }

    /**
     * 取两个日期之间的毫秒数
     * 返回正整数
     *
     * @param one
     * @param two
     * @return
     */
    public static long getMillisBetweenDays(Date one, Date two) {
        if (one == null || two == null) {
            return 0;
        }
        long o = one.getTime();
        long t = two.getTime();
        return o - t < 0 ? t - o : o - t;
    }

    /**
     * 获得一定天数的毫秒数
     *
     * @param days 天数
     * @return
     */
    public static long getMillisFromDays(int days) {
        return Period.days(days).toStandardDuration().getMillis();
    }

    /**
     * 获得之前日期
     *
     * @param date
     * @param remainDays
     * @return
     */
    public static Date getDateBefore(Date date, int remainDays) {
        return new DateTime(date).minusDays(remainDays).toDate();
    }

    /**
     * 获得之后日期
     *
     * @param date
     * @param remainDays
     * @return
     */
    public static Date getDateAfter(Date date, int remainDays) {
        return new DateTime(date).plusDays(remainDays).toDate();
    }

    /**
     * 获得之前日期
     *
     * @param date
     * @param remainDays
     * @return
     */
    public static Date getDateBefore(Date date, int remainDays, int remainHours) {
        return new DateTime(date).minusDays(remainDays).minusHours(remainHours).toDate();
    }

    /**
     * 获取之前的年份
     */
    public static Date getYearBefore(Date date, int years) {
        return new DateTime(date).minusYears(years).toDate();
    }


    /**
     * 获得到期日
     *
     * @param date
     * @param remainDays
     * @return
     */
    public static Date getExpiryDate(Date date, int remainDays) {
        return new DateTime(date).plusDays(remainDays).toDate();
    }

    /**
     * 获得几分钟后的时间
     *
     * @param date
     * @param minutes
     * @return
     */
    public static Date getDateAfter(Date date, int minutes, int seconds) {
        return new DateTime(date).plusMinutes(minutes).plusSeconds(seconds).toDate();
    }

    /**
     * 获得几毫秒的时间字符串
     *
     * @param date
     * @param millis
     * @return
     */
    public static String getDateAfterStr(Date date, int millis) {
        return new DateTime(date).plusMillis(millis).toString(TIME_FORMAT);
    }

    /**
     * 获得今天开始时间
     *
     * @return
     */
    public static Date getStartOfDay() {
        return DateTime.now().withTimeAtStartOfDay().toDate();
    }

    public static Date getStartOfDay(Date date) {
        return new DateTime(date.getTime()).withTimeAtStartOfDay().toDate();
    }

    /**
     * 获取月底时间
     */
    public static Date getEndOfMonth() {
        return DateTime.now().plusMonths(1).dayOfMonth().setCopy(1).millisOfDay().setCopy(0).toDate();
    }

    /**
     * 获取指定时间的月初时间
     */
    public static Date getMinMonthDate(String date, String parten) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateTime.parse(date, DateTimeFormat.forPattern(parten)).toDate());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return parse(dateFormat.get().format(calendar.getTime()), DateUtil.DATE_FORMAT);
    }

    /**
     * 获取指定时间的月末时间
     */
    public static Date getMaxMonthDate(String date, String parten) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateTime.parse(date, DateTimeFormat.forPattern(parten)).toDate());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date date1 = parse(dateFormat.get().format(calendar.getTime()), DateUtil.DATE_FORMAT);
        return getEndOfDay(date1);
    }

    public static Date getMaxMonthDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date date1 = parse(dateFormat.get().format(calendar.getTime()), DateUtil.DATE_FORMAT);
        return getEndOfDay(date1);
    }

    public static Date getStartOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getStartOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获得今天结束时间
     *
     * @return
     */
    public static Date getEndOfDay() {
        return DateTime.now().withTimeAtStartOfDay().plusDays(1).minus(1000).toDate();
    }

    public static Date getEndOfDay(Date date) {
        return new DateTime(date.getTime()).withTimeAtStartOfDay().plusDays(1).minus(1000).toDate();
    }

    /**
     * 获取指定偏移日期的结束时间
     *
     * @param offset -2 前天, -1 昨天, 0 今天, 1 明天, 2 后天 ...
     * @return
     */
    public static Date getEndOfDay(int offset) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, offset);
        return DateUtil.getEndOfDay(calendar.getTime());
    }

    /**
     * 计算剩余时间
     *
     * @param date
     * @param remainDays
     * @return
     */
    public static long remainTime(Date date, int remainDays) {
        return new DateTime(date).plusDays(remainDays).minus(now().getTime()).getMillis();
    }

//    /**
//     * 计算剩余时间秒数
//     *
//     * @param date
//     * @param remainDays
//     * @return
//     */
//    public static long remainTimeSec(Date date, int remainDays) {
//        return NumberUtil.getSecbyMillis(remainTime(date, remainDays));
//    }

    /**
     * 计算当前日期到截止日期的剩余时间毫秒数
     *
     * @param endDate 截止日期
     * @return
     */
    public static long remainTime(Date endDate) {
        return new DateTime(endDate).minus(now().getTime()).getMillis();
    }


//    /**
//     * 计算当前日期到截止日期的剩余时间秒数
//     *
//     * @param endDate 截止日期
//     * @return
//     */
//    public static long remainTimeSec(Date endDate) {
//        return NumberUtil.getSecbyMillis(remainTime(endDate));
//    }


    /**
     * 计算剩余时间
     *
     * @param date
     * @param remainDays
     * @return
     */
    public static String remainTimeStr(Date date, int remainDays) {
        Period period = new Period(DateTime.now().getMillis(), new DateTime(date).plusDays(remainDays).getMillis(), PeriodType.dayTime());
        PeriodFormatter daysAndHours = new PeriodFormatterBuilder()
                .appendPrefix("还剩")
                .printZeroIfSupported()
                .appendDays()
                .appendSeparator("天")
                .appendHours()
                .appendSuffix("小时")
                .toFormatter();
        return daysAndHours.print(period);
    }


    /**
     * 订单剩余支付时间
     *
     * @return 剩余时间毫秒数
     */
    public static long remainPayTime(Date date) {
        return remainTime(date, 3);
    }

    /**
     * 订单剩余支付时间
     *
     * @return 剩余时间字符串
     */
    public static String remainPayTimeStr(Date date) {
        Period period = new Period(DateTime.now().getMillis(), new DateTime(date).plusDays(3).getMillis(), PeriodType.dayTime());
        PeriodFormatter daysAndHours = new PeriodFormatterBuilder()
                .appendPrefix("还剩")
                .printZeroIfSupported()
                .appendDays()
                .appendSeparator("天")
                .appendHours()
                .appendSuffix("小时")
                .toFormatter();
        return daysAndHours.print(period);
    }

    /**
     * 订单剩余确认收货时间
     *
     * @param date
     * @return 剩余时间毫秒数
     */
    public static long remainReceiveTime(Date date, int extendTime) {
        return remainTime(date, 15 + extendTime);
    }

    /**
     * 订单剩余确认收货时间
     *
     * @param date
     * @return
     */
    public static String remainReceiveTimeStr(Date date, int extendTime) {
        Period period = new Period(DateTime.now().getMillis(), new DateTime(date).plusDays(15 + extendTime).getMillis(), PeriodType.dayTime());
        PeriodFormatter daysAndHours = new PeriodFormatterBuilder()
                .appendPrefix("还剩")
                .printZeroIfSupported()
                .appendDays()
                .appendSeparator("天")
                .appendHours()
                .appendSuffix("小时")
                .toFormatter();
        return daysAndHours.print(period);
    }


    /**
     * 获得退款流程中的过期时间
     *
     * @param date
     * @param remainDays
     * @return
     */
    public static String refundDeadline(Date date, int remainDays) {
        return new DateTime(date).plusDays(remainDays).toString(DATETIME_FORMAT);
    }

    /**
     * 判断当前时间是否已经为指定时间30天之内
     *
     * @param date
     * @return
     */
    public static boolean isWithin30Days(Date date) {
        if (date != null) {
            return new DateTime(date).plusDays(30).isAfterNow();
        }
        return false;
    }

    /**
     * 获取当前时间间隔num分钟后的00秒date
     *
     * @param date 给定的当前挤出时间
     * @param num  分钟偏移量，负数：前前偏移，0：当前分钟的00秒；整数：向后偏移
     * @return
     */
    public static Date getFirstSecTime(Date date, int num) {
        Date beinTime = null;

        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MINUTE, num);
            beinTime = cal.getTime();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:00", Locale.CHINESE);
            String beinTimeStr = df.format(beinTime);
            beinTime = df.parse(beinTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return beinTime;
    }

    /**
     * 获取给定时间的分钟字符串
     *
     * @param date 给定的时间
     * @return 返回 “yyyyMMddHHmm” 样式的字符串，精确到分钟
     */
    public static String getMinuteTimeStr(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm", Locale.CHINESE);
        return df.format(date);
    }


    public static Date yearChange(Date date, Integer year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    public static int differentDaysByMillisecond(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    public static Date getNowDateForYYYYMMDD() {
        String now = DateUtil.toDateString(new Date(), DateUtil.DATE_FORMAT) + SUFFIX;
        return DateUtil.parse(now);
    }

    /**
     * @Description: 判断 该区间日期相差几年
     * @Date: 2017/11/6
     */

    public static int yearsBetween(String start, String end) throws ParseException {
        int result;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(sdf.parse(start));
        c2.setTime(sdf.parse(end));
        result = c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
        return result;
    }

    /**
     * 判断时间段是否在三个月内
     *
     * @return int
     * @throws ParseException
     */
    public static Boolean monthsBetween(String start, String end) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(sdf.parse(start));
        c2.setTime(sdf.parse(end));
        c2.add(Calendar.MONTH, -3);
        Date date1 = c1.getTime();
        Date date2 = c2.getTime();

        if (date2.compareTo(date1) <= 0) {
            return false;
        }
        return true;
    }

    public static Date getDateStartBeforeSomeDays(Date dateEnd, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateEnd);
        Date dateStart;
        calendar.add(Calendar.DATE, -days);
        dateStart = calendar.getTime();
        return dateStart;
    }

    public static List<String> dateSplitMonth(Date startDate, Date endDate, String type) {
        List<String> dateList = new ArrayList<String>();
        if (startDate != null) {
            while (startDate.getTime() < endDate.getTime()) {
                dateList.add(toDateString(endDate, DATE_FORMAT2));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(endDate);
                calendar.add(Calendar.MONTH, -1);
                endDate = calendar.getTime();

            }
        }
        if (!dateList.contains(toDateString(startDate, DATE_FORMAT2))) {
            dateList.add(toDateString(startDate, DATE_FORMAT2));
        }
        return dateList;
    }

    /**
     * 获取短时间类型
     *
     * @param datehhmmss
     * @return
     */
    public static Date getDateShort(Date datehhmmss) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(datehhmmss);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime2 = formatter.parse(dateString, pos);
        return currentTime2;
    }

    /**
     * 日期格式校验
     * <p>
     * JJG
     *
     * @param date
     * @return
     */
    public static boolean isDate(String date) {
        /**
         * 判断日期格式和范围
         */
        String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";

        Pattern pat = Pattern.compile(rexp);

        Matcher mat = pat.matcher(date);

        boolean dateType = mat.matches();

        return dateType;
    }

}
