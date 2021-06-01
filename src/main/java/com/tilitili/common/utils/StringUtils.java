package com.tilitili.common.utils;

public class StringUtils {
    public static boolean isNumber(String str) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("[0-9]*");
        java.util.regex.Matcher match = pattern.matcher(str.trim());
        return match.matches();
    }
}
