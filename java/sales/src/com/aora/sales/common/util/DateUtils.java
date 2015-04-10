package com.aora.sales.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private DateUtils() {
    }

    private final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private static String YYYY_MM_DD = "yyyy-MM-dd";
    
    private static String YYYY_MM = "yyyy.MM";

    public static String formatToTime(Date date) {
        if (null == date)
            return "";
        else
            return getFormatter(YYYY_MM_DD_HH_MM_SS).format(date);
    }
    
    public static String formatToDay(Date date) {
        if (null == date)
            return "";
        else
            return getFormatter(YYYY_MM_DD).format(date);
    }
    
    public static String formatToMonth(Date date){
        if (null == date)
            return "";
        else
            return getFormatter(YYYY_MM).format(date);
    }

    private static SimpleDateFormat getFormatter(String parttern) {
        return new SimpleDateFormat(parttern);
    }
    
}
