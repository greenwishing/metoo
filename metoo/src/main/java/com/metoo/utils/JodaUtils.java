package com.metoo.utils;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/7
 */
public class JodaUtils {

    public static final String DATE_FORMAT = "YYYY-MM-dd";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String DATE_TIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;

    public static String localDateToString(LocalDate localDate) {
        if (localDate == null) return null;
        return localDate.toString(DATE_FORMAT);
    }

    public static String dateTimeToString(DateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.toString(DATE_TIME_FORMAT);
    }
}
