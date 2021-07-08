package com.tilitili.common.utils;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.tomcat.util.codec.binary.StringUtils.getBytesUtf8;

public class StringUtils {
    public static boolean isNumber(String str) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("[0-9]*");
        java.util.regex.Matcher match = pattern.matcher(str.trim());
        return match.matches();
    }

    public static String md5(String source) {
        return md5(getBytesUtf8(source));
    }

    public static String md5(byte[] source) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(source);
            return Hex.encodeHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String patten(String regex, String input) {
        Matcher matcher = Pattern.compile(regex).matcher(input);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    public static List<String> extractList(String regex, String input) {
        Matcher matcher = Pattern.compile(regex).matcher(input);
        List<String> result = new ArrayList<>();
        if (matcher.find()) {
            for (int index = 1; index <= matcher.groupCount(); index ++) {
                result.add(matcher.group(index));
            }
        }
        return result;
    }

    public static String convertCnNumber(String s) {
        String[] numberMap = "零一二三四五六七八九".split("");
        for (int index = 0; index < numberMap.length; index++) {
            s = s.replaceAll(numberMap[index], String.valueOf(index));
        }
        return s;
    }
}
