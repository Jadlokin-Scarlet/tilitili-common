package com.tilitili.common.emnus;

import com.tilitili.common.entity.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TaskType {
    SpiderVideo(0, "自定义爬取"),
    BatchSpiderVideo(1, "自定义批量爬取"),
    AutoBatchSpiderVideo(2, "定时任务"),
    ;

    private TaskType(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    private Integer value;
    private String text;

    public static List<Resource> getResource() {
        return Arrays.stream(values())
                .map(e -> new Resource(e.value, e.text))
                .collect(Collectors.toList());
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
