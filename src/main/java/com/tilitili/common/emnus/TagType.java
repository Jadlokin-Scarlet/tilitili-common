package com.tilitili.common.emnus;

import com.tilitili.common.entity.resource.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public enum TagType {
    NORMAL_TAG(0, "普通tag"),
    TOUHOU_TAG(1, "车万Tag")
    ;
    TagType(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public final Integer value;
    public final String text;

    public static TagType getByValue(Integer value) {
        for (TagType taskType : values()) {
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
