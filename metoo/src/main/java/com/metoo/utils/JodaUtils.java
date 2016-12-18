package com.metoo.utils;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/7
 */
public class JodaUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String DATE_TIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;

    private static final String DATE_REGEX = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
    private static final String TIME_REGEX = "^([0-1]?[0-9]|2[0-3]):([0-5][0-9])$";

    public static String localDateToString(LocalDate localDate) {
        if (localDate == null) return null;
        return localDate.toString(DATE_FORMAT);
    }

    public static String dateTimeToString(DateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.toString(DATE_TIME_FORMAT);
    }

    public static LocalDate parseLocalDate(String text) {
        return parseLocalDate(text, DATE_FORMAT);
    }

    public static LocalDate parseLocalDate(String text, String patten) {
        if (StringUtils.hasText(text)) {
            Date date = parseDate(text, patten);
            return new LocalDate(date);
        }
        return null;
    }

    public static Date parseDate(String text, String patten) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(patten);
            return dateFormat.parse(text);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Date string can not be parsed:" + text, e);
        }
    }

    public static DateTime parseDateTime(String val) {
        return parseDateTime(val, DATE_TIME_FORMAT);
    }

    public static DateTime parseDateTime(String text, String patten) {
        if (!StringUtils.hasText(text)) {
            return null;
        }
        return DateTimeFormat.forPattern(patten).parseDateTime(text);
    }

    public static boolean isValidDate(String sDate) {
        return StringUtils.hasText(sDate) && sDate.matches(DATE_REGEX);
    }

    public static boolean isValidTime(String sTime) {
        return StringUtils.hasText(sTime) && sTime.matches(TIME_REGEX);
    }

    public static boolean isValidDateTime(String sDatetime) {
        if (StringUtils.hasText(sDatetime) && sDatetime.length() == 16) {
            String date = sDatetime.substring(0, 10);
            String time = sDatetime.substring(11);
            if (isValidDate(date) && isValidTime(time)) {
                return true;
            }
        }
        return false;
    }
}
