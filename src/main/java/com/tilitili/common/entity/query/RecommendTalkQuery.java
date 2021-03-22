package com.tilitili.common.entity.query;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RecommendTalkQuery extends BaseQuery<RecommendTalkQuery> {
    private Integer id;
    private Integer status;
    private Integer type;
    private Integer issueId;
}
