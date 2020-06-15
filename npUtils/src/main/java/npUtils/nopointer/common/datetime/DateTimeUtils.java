package npUtils.nopointer.common.datetime;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间 工具类
 */
public class DateTimeUtils {


    public static void main(String[] aaa) {
//        System.out.println(getDateWithFormatByMillisecond(System.currentTimeMillis(), "MM-dd"));
        System.out.println(getAgeFromBirthTime("1993-12-12"));
        try {
            Date date = getTheWeekAfterDate("2020-06-15", "yyyy-MM-dd", 1);
            System.out.println(getDateWithFormatBySecond(date, "yyyy-MM-dd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void DateFormatUtils() {
    }


    /**
     * 获取当前日期
     *
     * @param format 指定格式
     * @return
     */
    public static String getCurrentDateWithFormat(String format) {
        return DateFormatUtils.format(System.currentTimeMillis(), format);
    }

    /**
     * 获取当前日期 默认返回yyyy-MM-dd
     *
     * @return
     */
    public static String getCurrentDateWithyyyyMMdd() {
        return getCurrentDateWithFormat("yyyy-MM-dd");
    }


    /**
     * 根据年月日获取时间戳，单位秒
     *
     * @param yyyyMMddString 日期 比如2020-02-02
     * @return
     */
    public static long getTimestampWithSecondByDate(String yyyyMMddString) {
        try {
            Date date = DateUtils.parseDate(yyyyMMddString, new String[]{"yyyy-MM-dd"});
            return date.getTime() / 1000L;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据年月日获取时间戳，单位毫秒
     *
     * @param yyyyMMddString 日期 比如2020-02-02
     * @return
     */
    public static long getTimestampWithMillisecondByDate(String yyyyMMddString) {
        try {
            Date date = DateUtils.parseDate(yyyyMMddString, new String[]{"yyyy-MM-dd"});
            return date.getTime() / 1000L;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 根据时间戳获取年月日
     *
     * @param timestamp 时间戳 秒
     * @return
     */
    public static String getDateBySecond(long timestamp) {
        return DateFormatUtils.format(timestamp * 1000L, "yyyy-MM-dd");
    }


    /**
     * 根据时间戳获取年月日 时分秒（2020-12-31 12:12:12）
     *
     * @param timestampWithMillisecond 时间戳 单位毫秒
     * @return
     */
    public static String getDateWithyyyyMMddByMillisecond(long timestampWithMillisecond) {
        return DateFormatUtils.format(timestampWithMillisecond, "yyyy-MM-dd");
    }

    /**
     * 根据时间戳获取年月日
     *
     * @param timestampWithSecond 时间戳 单位秒
     * @return
     */
    public static String getDateWithyyyyMMddBySecond(long timestampWithSecond) {
        return getDateWithyyyyMMddByMillisecond(timestampWithSecond * 1000L);
    }


    /**
     * 按指定的日期格式 返回数据
     *
     * @param date
     * @param dateFormat 自定义格式化
     * @return
     */
    public static String getDateWithFormatBySecond(Date date, String dateFormat) {
        return DateFormatUtils.format(date, dateFormat);
    }

    /**
     * 按指定的日期格式 返回数据
     *
     * @param timestampWithSecond 时间戳 单位秒
     * @param dateFormat          自定义格式化
     * @return
     */
    public static String getDateWithFormatBySecond(long timestampWithSecond, String dateFormat) {
        return getDateWithFormatByMillisecond(timestampWithSecond * 1000L, dateFormat);
    }

    /**
     * 按指定的日期格式 返回数据
     *
     * @param timestampWithMillisecond 时间戳 单位毫秒
     * @param dateFormat               自定义格式化
     * @return
     */
    public static String getDateWithFormatByMillisecond(long timestampWithMillisecond, String dateFormat) {
        return DateFormatUtils.format(timestampWithMillisecond, dateFormat);
    }


    private static Date get(Date date, int dateType, int after) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(dateType, after);
        return c.getTime();
    }


    /**
     * 获取指定的日期的前/后日期（单位分钟）
     *
     * @param date  指定的日期
     * @param after 正数表示多少分钟以后 负数表示多少分钟以前 0 表示指定的日期
     * @return
     */
    public static Date getTheMinuteAfterDate(Date date, int after) {
        return get(date, Calendar.MINUTE, after);
    }

    /**
     * 获取指定的日期的前/后日期（单位分钟）
     *
     * @param dateStr 指定的日期
     * @param format  指定的日期的格式化
     * @param after   正数表示多少小时以后 负数表示多少小时以前 0 表示指定的日期
     * @return
     */
    public static Date getTheMinuteAfterDate(String dateStr, String format, int after) throws ParseException {
        Date date = DateUtils.parseDate(dateStr, new String[]{format});
        return getTheMinuteAfterDate(date, after);
    }

    /**
     * 获取指定的日期的前/后日期（单位小时）
     *
     * @param date  指定的日期
     * @param after 正数表示多少小时以后 负数表示多少小时以前 0 表示指定的日期
     * @return
     */
    public static Date getTheHourAfterDate(Date date, int after) {
        return get(date, Calendar.HOUR, after);
    }

    /**
     * 获取指定的日期的前/后日期（单位小时）
     *
     * @param dateStr 指定的日期
     * @param format  指定的日期的格式化
     * @param after   正数表示多少小时以后 负数表示多少小时以前 0 表示指定的日期
     * @return
     */
    public static Date getTheHourAfterDate(String dateStr, String format, int after) throws ParseException {
        Date date = DateUtils.parseDate(dateStr, new String[]{format});
        return getTheHourAfterDate(date, after);
    }


    /**
     * 获取指定的日期的前/后日期（单位天）
     *
     * @param date  指定的日期
     * @param after 正数是后面的天数，负数是前面的天数，0是指定的那一天
     * @return
     */
    public static Date getTheDayAfterDate(Date date, int after) {
        return get(date, Calendar.DATE, after);
    }

    /**
     * 获取指定的日期的前/后日期（单位天）
     *
     * @param dateStr 指定的日期
     * @param format  指定的日期的格式化
     * @param after   正数是后面的天数，负数是前面的天数，0是指定的那一天
     * @return
     * @throws ParseException
     */
    public static Date getTheDayAfterDate(String dateStr, String format, int after) throws ParseException {
        Date date = DateUtils.parseDate(dateStr, new String[]{format});
        return getTheDayAfterDate(date, after);
    }

    /**
     * 获取指定的日期的前/后日期（单位周）
     *
     * @param date
     * @param after
     * @return
     */
    public static Date getTheWeekAfterDate(Date date, int after) {
        return get(date, Calendar.DATE, after * 7);
    }

    /**
     * 获取指定的日期的前/后日期（单位周）
     *
     * @param dateStr
     * @param format
     * @param after
     * @return
     */
    public static Date getTheWeekAfterDate(String dateStr, String format, int after) throws ParseException {
        Date date = DateUtils.parseDate(dateStr, new String[]{format});
        return getTheWeekAfterDate(date, after);
    }


    /**
     * 获取指定的日期的前/后日期（单位月）
     *
     * @param date
     * @param after
     * @return
     */
    public static Date getTheMonthAfterDate(Date date, int after) {
        return get(date, Calendar.MONTH, after);
    }

    /**
     * 获取指定的日期的前/后日期（单位月）
     *
     * @param dateStr
     * @param format
     * @param after
     * @return
     */
    public static Date getTheMonthAfterDate(String dateStr, String format, int after) throws ParseException {
        Date date = DateUtils.parseDate(dateStr, new String[]{format});
        return getTheMonthAfterDate(date, after);
    }


    /**
     * 获取指定的日期的前/后日期（单位年）
     *
     * @param date
     * @param after
     * @return
     */
    public static Date getTheYearAfterDate(Date date, int after) {
        return get(date, Calendar.YEAR, after);
    }

    /**
     * 获取指定的日期的前/后日期（单位年）
     *
     * @param dateStr
     * @param format
     * @param after
     * @return
     */
    public static Date getTheYearAfterDate(String dateStr, String format, int after) throws ParseException {
        Date date = DateUtils.parseDate(dateStr, new String[]{format});
        return getTheYearAfterDate(date, after);
    }


    /**
     * 获取某年某月的天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getYearMonthDayCount(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 0); //输入类型为int类型
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 根据出生年月日计算年龄
     *
     * @param birthTimeString yyyy-MM-dd 格式
     * @return
     */
    public static int getAgeFromBirthTime(String birthTimeString) {
        if (StringUtils.isEmpty(birthTimeString)) return 0;
        // 先截取到字符串中的年、月、日
        String strs[] = birthTimeString.trim().split("-");
        int selectYear = Integer.parseInt(strs[0]);
        // 得到当前时间的年、月、日
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        // 用当前年月日减去生日年月日
        int yearMinus = yearNow - selectYear;
        int age = yearMinus;// 先大致赋值
        return age;
    }


    /**
     * 根据时间戳计算年龄
     *
     * @param birthTimeLong 时间戳 单位秒
     * @return
     */
    public static int getAgeFromBirthdayTime(long birthTimeLong) {
        Date date = new Date(birthTimeLong * 1000l);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String birthTimeString = format.format(date);
        return getAgeFromBirthTime(birthTimeString);
    }

}
