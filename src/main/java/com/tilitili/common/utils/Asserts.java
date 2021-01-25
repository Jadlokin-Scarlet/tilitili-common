package com.tilitili.common.utils;

import org.apache.logging.log4j.util.Strings;
import org.springframework.util.StringUtils;

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
            throw new IllegalStateException(name + " is null");
        }
    }

    public static void notEmpty(String s, String name) {
        if (StringUtils.isEmpty(s)) {
            throw new IllegalStateException(name + " is empty");
        }
    }

    public static void notBlank(String s, String name) {
        if (Strings.isBlank(s)) {
            throw new IllegalStateException(name + " is blank");
        }
    }
}

