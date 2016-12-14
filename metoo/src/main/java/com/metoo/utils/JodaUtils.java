package com.metoo.utils;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}
