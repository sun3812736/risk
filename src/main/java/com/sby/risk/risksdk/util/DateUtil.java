package com.sby.risk.risksdk.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  DateUtil
 */
public class DateUtil {

    /**
     *
     * @return currentTimeMillis
     */
    public static long getCurrentTime(){
        long time = System.currentTimeMillis();
        return time ;
    }

    /**
     * getDate
     * yyyy-MM-dd HH:mm:ss
     *
     * @param
     * @param format
     *            如yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getDateString(long milliseconds, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(milliseconds));
    }
    public static Timestamp timestap(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return    Timestamp.valueOf(sdf.format(new Date()));
    }
}
