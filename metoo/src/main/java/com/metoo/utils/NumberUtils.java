package com.metoo.utils;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/14
 */
public class NumberUtils {

    public static final NumberFormat NUMBER_ZERO_FORMAT = buildNumberFormat(2, 2);
    public static final NumberFormat NUMBER_FORMAT = buildNumberFormat(0, 2);

    public static NumberFormat buildNumberFormat(int min, int max) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumFractionDigits(min);
        numberFormat.setMaximumFractionDigits(max);
        numberFormat.setGroupingUsed(false);
        numberFormat.setRoundingMode(RoundingMode.HALF_UP);
        return numberFormat;
    }

    public static String toString(BigDecimal value) {
        return toString(value, 2);
    }

    public static String toString(BigDecimal value, int digits) {
        if (value != null) {
            if (digits == 2) {
                return NUMBER_ZERO_FORMAT.format(value);
            }
            return buildNumberFormat(digits, digits).format(value);
        }
        return null;
    }

    public static String toRealString(BigDecimal value) {
        return toString(value, 2);
    }

    public static String toRealString(BigDecimal value, int digits) {
        if (value != null) {
            if (digits == 2) {
                return NUMBER_FORMAT.format(value);
            }
            return buildNumberFormat(digits, digits).format(value);
        }
        return null;
    }

    private static final String BIG_DECIMAL_REGEX = "^(-?\\d{1,10})||(-?\\d{1,10}\\.\\d{1,2})$";

    private static final String POSITIVE_BIG_DECIMAL_REGEX = "^\\d{1,10}(\\.\\d{1,2})?$";

    private static final String INTEGER_REGEX = "^-?\\d{1,10}$";

    private static final String POSITIVE_INTEGER_REGEX = "^\\d{1,10}$";

    public static boolean isBigDecimal(String value) {
        return StringUtils.hasLength(value) && value.matches(BIG_DECIMAL_REGEX);
    }

    public static boolean isPositiveBigDecimal(String value) {
        return StringUtils.hasLength(value) && value.matches(POSITIVE_BIG_DECIMAL_REGEX);
    }

    public static boolean isInteger(String value) {
        return StringUtils.hasLength(value) && value.matches(INTEGER_REGEX);
    }

    public static boolean isPositiveInteger(String value) {
        return StringUtils.hasLength(value) && value.matches(POSITIVE_INTEGER_REGEX);
    }
}
