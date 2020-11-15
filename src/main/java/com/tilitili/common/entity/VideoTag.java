package com.tilitili.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain=true)
public class VideoTag implements Serializable {
    private Long av;

    private String name;

    private List<String> tags;

    public VideoTag setTag(String tag) {
        this.tags.add(tag);
        return this;
    }

    private static final long serialVersionUID = 1L;
}