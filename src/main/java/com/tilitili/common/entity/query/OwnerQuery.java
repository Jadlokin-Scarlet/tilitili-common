package com.tilitili.common.entity.query;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class OwnerQuery extends BaseQuery<OwnerQuery> {
    private Long uid;
    private String name;
}
