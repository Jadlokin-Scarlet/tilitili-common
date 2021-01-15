package com.tilitili.common.entity.query;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class TagQuery extends BaseQuery<TagQuery> {
    private Long id;
    private String name;
    private Integer status;
    private Integer type;
}
