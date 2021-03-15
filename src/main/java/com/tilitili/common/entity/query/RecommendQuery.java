package com.tilitili.common.entity.query;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RecommendQuery extends BaseQuery<RecommendQuery> {
    private Long av;
    private Integer status;
    private Integer issueId;
    private Integer videoStatus;
}
