package com.tilitili.common.emnus;

import com.tilitili.common.entity.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public enum ResourcesType {
    IS_STAFF_SHOW_1("IS_STAFF_SHOW_1", "是否展示staff1"),
    IS_STAFF_SHOW_2("IS_STAFF_SHOW_2", "是否展示staff2"),
    MUSIC_NAME("MUSIC_NAME", "ed名"),
    MUSIC_OWNER("MUSIC_OWNER", "ed社团"),
    MUSIC_CARD("MUSIC_CARD", "ed专辑"),
    MUSIC_IMAGE("MUSIC_IMAGE", "ed图片"),
    MUSIC_SOURCE("MUSIC_SOURCE", "ed原曲")
    ;

    ResourcesType(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public final String value;
    public final String text;

    public static ResourcesType getByValue(String value) {
        for (ResourcesType taskType : values()) {
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

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }


}
