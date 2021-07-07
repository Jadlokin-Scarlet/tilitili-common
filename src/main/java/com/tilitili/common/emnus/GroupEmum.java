package com.tilitili.common.emnus;

import com.tilitili.common.entity.resource.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public enum GroupEmum {

    RANK_GROUP(670290958L, "排行榜数据小组Project R"),
    ;

    GroupEmum(Long value, String text) {
        this.value = value;
        this.text = text;
    }

    public final Long value;
    public final String text;

    public static GroupEmum getByValue(String value) {
        for (GroupEmum group : values()) {
            if (Objects.equals(group.value, value)) {
                return group;
            }
        }
        return null;
    }

    public static List<Resource> getResource() {
        return Arrays.stream(values())
                .map(e -> new Resource(e.value, e.text))
                .collect(Collectors.toList());
    }

    public Long getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

}
