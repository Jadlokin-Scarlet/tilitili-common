package com.tilitili.common.entity.query;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class TaskQuery extends BaseQuery<TaskQuery> {
    private Long id;
    private Long av;
    private Integer status;
    private Integer type;
    private Long batchId;
}
