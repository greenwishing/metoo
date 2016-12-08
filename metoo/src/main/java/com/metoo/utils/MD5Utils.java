package com.metoo.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/04
 */
public class MD5Utils {
    public static final String MD5 = "MD5";

    public static final String EMPTY_STRING = MD5Utils.encode("");

    public static String encode(String value) {
        if (value == null) return "";
        return encode(value.getBytes());
    }

    public static String encode(String value, String charset) {
        if (value == null) return "";
        try {
            return encode(value.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encode(byte[] value) {
        return encode(MD5, value);
    }

    private static String encode(String algorithm, byte[] value) {
        if (value == null) {
            return "";
        }
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(value);
            byte[] bytes = digest.digest();

            int i;
            StringBuilder buf = new StringBuilder("");
            for (byte aByte : bytes) {
                i = aByte;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new UnsupportedOperationException("No " + algorithm + " algorithm!");
        }
    }


    public static boolean is32Md5(String str) {
        return isMd5(str, 32);
    }

    public static boolean is16Md5(String str) {
        return isMd5(str, 16);
    }

    public static boolean isMd5(String str, int len) {
        return null != str && str.length() == len && str.matches("[a-fA-F0-9]+");
    }
}
