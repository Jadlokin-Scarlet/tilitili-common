package com.tilitili.common.entity.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class VideoDataQuery extends BaseQuery<VideoDataQuery> {
    private Long av;
    private Integer issue;
    private Integer status;
    private Boolean hasRank;

    //info
    private Boolean isDelete;
    private String name;
    private String owner;
    private String bv;
    private String type;
    private Boolean copyright;
    private String firstType;
    private Integer minDuration;
    private Integer maxDuration;
    private Boolean isRandom;

}


