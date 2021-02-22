package com.tilitili.common.entity.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class TagRelationGroup {
    private String tag;
    private Integer number;
}
