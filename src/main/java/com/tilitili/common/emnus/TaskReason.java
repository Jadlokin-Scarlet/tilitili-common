package com.tilitili.common.emnus;

import com.tilitili.common.entity.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public enum TaskReason {
    NO_REASON(0, "我不管我就是要爬", "SpiderVideoViewTaskMessage"),
    SUPPLEMENT_VIDEO(1, "补充缺失视频", "SpiderVideoViewTaskMessage"),
    SUPPLEMENT_VIDEO_INFO(2, "补充视频信息", "SpiderVideoViewTaskMessage"),
    RE_SPIDER_HIDDEN_VIDEO(3, "重爬被隐藏的视频", "SpiderVideoViewTaskMessage"),
    RE_SPIDER_All_VIDEO(4, "重爬所有的视频", "SpiderVideoViewTaskMessage"),
    RE_SPIDER_All_VIDEO_TAG(5, "重爬所有的视频的tag", "SpiderVideoTagTaskMessage"),
    SUPPLEMENT_VIDEO_TAG(6, "补充视频tag", "SpiderVideoTagTaskMessage"),
    ;

    TaskReason(Integer value, String text, String destination) {
        this.value = value;
        this.text = text;
        this.destination = destination;
    }

    public final Integer value;
    public final String text;
    public final String destination;

    public static TaskReason getByValue(Integer value) {
        for (TaskReason taskReason : values()) {
            if (Objects.equals(taskReason.value, value)) {
                return taskReason;
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

    public String getDestination() {
        return destination;
    }
}
