package com.tilitili.common.emnus;

import com.tilitili.common.entity.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TaskReason {
    NO_REASON(0, "我不管我就是要爬"),
    SUPPLEMENT_VIDEO(1, "补充缺失视频"),
    SUPPLEMENT_VIDEO_INFO(2, "补充视频信息"),
    ;

    private TaskReason(Integer value, String text) {
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
