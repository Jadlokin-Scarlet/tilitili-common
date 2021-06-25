package com.tilitili.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;
import java.util.function.Predicate;

@Slf4j
public class StreamUtil {
    @FunctionalInterface
    public interface ThrowingFunction<T, R, E extends Exception> {
        R accept(T t) throws E;
    }
    @FunctionalInterface
    public interface ThrowingRunning<E extends Exception> {
        void run() throws E;
    }

    public static <T, R, E extends Exception> Function<T, R> tryMap(ThrowingFunction<T, R, E> throwingFunction) {
        return i -> {
            try {
                return throwingFunction.accept(i);
            } catch (Exception ex) {
                log.error("lambda内部异常", ex);
                return null;
            }
        };
    }

    public static <E extends Exception> Runnable tryRun(ThrowingRunning<E> throwingRunning) {
        return () -> {
            try {
                throwingRunning.run();
            } catch (Exception ex) {
                log.error("lambda内部异常", ex);
            }
        };
    }

    public static <T, R> Predicate<T> isEqual(Function<T, R> getFun, R value) {
        return t -> Predicate.isEqual(value).test(getFun.apply(t));
    }
}
