package cn.xyspace.common.core.utils;

import org.joda.time.DateTime;

/**
 * 
 *
 * @author ChenFangjie(2015年7月11日 下午6:06:47)
 *
 *
 * @since 1.0.0
 *
 * @version 1.0.0
 *
 */
public class DateTimeUtils {

    /** 短日期格式（yyyy-MM-dd） */
    public static final String SHORT_DATE_STR = "yyyy-MM-dd";

    /** 短日期格式2（yyyyMMdd） */
    public static final String SHORT_DATE_STR2 = "yyyyMMdd";

    /** 长时间格式（yyyy-MM-dd HH:mm:ss） */
    public static final String LONG_TIME_STR = "yyyy-MM-dd HH:mm:ss";

    /**
     * 
     *
     * @return
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static final long getCurrentTimeInMillis() {
        return DateTime.now().getMillis();
    }

    /**
     * 返回当前日期的字符串表示形式。
     * 
     * @return 当前时间的字符串表示形式
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static final String getCurrentDateString() {
        return DateTime.now().toString(SHORT_DATE_STR2);
    }

    /**
     * 返回当前时间的字符串表示形式。
     * 
     * @return 当前时间的字符串表示形式
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static final String getCurrentTimeString() {
        return DateTime.now().toString(LONG_TIME_STR);
    }

}
