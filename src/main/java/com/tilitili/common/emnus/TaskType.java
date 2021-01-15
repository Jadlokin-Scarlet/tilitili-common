package com.tilitili.common.emnus;

import com.tilitili.common.entity.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public enum TaskType {
    SpiderVideo(0, "自定义爬取"),
    BatchSpiderVideo(1, "自定义批量爬取"),
    AutoBatchSpiderVideo(2, "定时任务"),
    ;

    TaskType(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public final Integer value;
    public final String text;

    public static TaskType getByValue(Integer value) {
        for (TaskType taskType : values()) {
            if (Objects.equals(taskType.value, value)) {
                return taskType;
            }
        }
        return null;
    }

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
