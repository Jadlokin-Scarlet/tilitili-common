package com.tilitili.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class RecommendVideo {
    private Integer id;
    private String name;
    private Date createTime;
    private Date updateTime;
    private Integer type;
    private Integer status;
    private Integer issue;

    //recommend表
    private Integer recommendNumber;
    private Integer selfRecommendNumber;
    private List<Recommend> recommendList;
    private List<Recommend> selfRecommendList;

    //recommend talk表
    private Boolean hasTalk;
}
