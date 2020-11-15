package com.tilitili.common.entity.query;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class VideoTagQuery extends BaseQuery<VideoTagQuery> {
    private Long av;
    private String name;
    private String tag;
}
