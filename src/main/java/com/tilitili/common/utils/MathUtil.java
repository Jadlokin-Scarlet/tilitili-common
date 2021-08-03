package com.tilitili.common.utils;

public class MathUtil {
    public static long ceilDiv(long a, long b) {
        double c = Math.ceil(1.0 * a / b);
        return (long) c;
    }
    public static int ceilDiv(int a, int b) {
        double c = Math.ceil(1.0 * a / b);
        return (int) c;
    }
}
