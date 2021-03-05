package com.tilitili.common.entity.query;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RecommendVideoQuery extends BaseQuery<RecommendVideoQuery> {
    private Integer type;
    private Integer status;
}
