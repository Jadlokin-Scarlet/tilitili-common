package com.tilitili.common.entity.query;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ResourcesQuery extends BaseQuery<ResourcesQuery> {
    private Long id;
    private Integer status;
    private Integer type;
}
