package com.tilitili.common.entity.query;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class VideoTagRelationQuery extends BaseQuery<VideoTagRelationQuery> {
    private Long id;
    private Long tagId;
    private Long av;
    private Integer status;
    private Integer type;
}
