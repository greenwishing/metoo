package com.metoo.utils;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/7
 */
public class JodaUtils {

    public static final String DATE_FORMAT = "YYYY-MM-dd";

    public static String localDateToString(LocalDate localDate) {
        if (localDate == null) return null;
        return localDate.toString(DATE_FORMAT);
    }

    public static String dateTimeToDateString(DateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.toString(DATE_FORMAT);
    }
}
