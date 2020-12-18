package com.tilitili.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain=true)
public class VideoTag implements Serializable {
    private Long av;

    private String name;

    private List<String> tags = new ArrayList<>();

    public VideoTag setTags(String tag) {
        if (!tag.isEmpty()) {
            this.tags.add(tag);
        }
        return this;
    }

    private static final long serialVersionUID = 1L;
}