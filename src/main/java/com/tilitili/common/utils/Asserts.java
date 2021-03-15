package com.tilitili.common.utils;

import org.apache.logging.log4j.util.Strings;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class Asserts {
    public Asserts() {
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    public static void isTrue(boolean expression, String message, Object... args) {
        if (!expression) {
            throw new IllegalStateException(String.format(message, args));
        }
    }

    public static void isTrue(boolean expression, String message, Object arg) {
        if (!expression) {
            throw new IllegalStateException(String.format(message, arg));
        }
    }

    public static void notNull(Object object, String name) {
        if (object == null) {
            throw new IllegalStateException(name + "未获取到");
        }
    }

    public static void notNull(Object object, String name, String msg) {
        if (object == null) {
            throw new IllegalStateException(name + "未获取到。" + msg);
        }
    }

    public static void checkNull(Object object, String message) {
        if (object != null) {
            throw new IllegalStateException(message);
        }
    }

    public static void notEmpty(String s, String name) {
        if (StringUtils.isEmpty(s)) {
            throw new IllegalStateException(name + "为空");
        }
    }

    public static void notBlank(String s, String name) {
        if (Strings.isBlank(s)) {
            throw new IllegalStateException(name + "为空字符串");
        }
    }

    public static void checkEquals(Object a, Object b, String message) {
        if (! Objects.equals(a, b)) {
            throw new IllegalStateException(message + ", ["+a+"]["+b+"]");
        }
    }
}

