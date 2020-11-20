package com.tilitili.common.entity.query;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class BatchTaskQuery extends BaseQuery<BatchTaskQuery> {
    private Long id;
    private Integer status;
    private Integer type;
    private Integer reason;

}
