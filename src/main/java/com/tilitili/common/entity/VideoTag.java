package com.tilitili.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain=true)
public class VideoTag {
    private Long av;
    private String name;
    private String tagIdListStr;
    private List<Tag> tagList = new ArrayList<>();
}