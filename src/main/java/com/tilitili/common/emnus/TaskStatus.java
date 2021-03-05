package com.tilitili.common.emnus;

import com.tilitili.common.entity.resource.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TaskStatus {
    DELETE(-1, "废弃"),
    WAIT(0, "待爬取"),
    SPIDER(1, "爬取中"),
    SUCCESS(2, "爬取成功"),
    FAIL(3, "爬取失败"),
    TIMEOUT(4, "爬取超时"),
    ;

    TaskStatus(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public final Integer value;
    public final String text;

    public static List<Resource> getResource() {
        return Arrays.stream(values())
                .map(e -> new Resource(e.value, e.text))
                .collect(Collectors.toList());
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
