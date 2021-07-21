package com.tilitili.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Setter
@Getter
@Accessors(chain = true)
public class VideoData {
    private Long av;
    private Integer issue;
    private Integer view;
    private Integer reply;
    private Integer favorite;
    private Integer coin;
    private Integer page;
    private Integer point;
    private Integer rank;
    private Integer danmaku;
    private Integer share;
    private Integer like;
    private Integer dislike;
    private String evaluation;
    private Integer level;
    private Date spiderTime;

    //info
    private String name;
    private String img;
    private String type;
    private String owner;
    private Boolean copyright;
    private String pubTime;
    private Long startTime;
    private String bv;
    private String description;
    private Long state;
    private Long attribute;
    private Long duration;
    private Long missionId;
    private String dynamic;
    private Boolean isDelete;
    private Integer startPage;
    private Integer status;
    private String externalOwner;
    private Boolean isCopyWarning;

    private Integer hisRank;
    private Boolean isLen;
}
