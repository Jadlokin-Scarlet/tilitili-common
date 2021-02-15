package com.tilitili.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class VideoData {
    private Long av;
    private Integer issue;
    private Integer view = 0;
    private Integer reply = 0;
    private Integer favorite = 0;
    private Integer coin = 0;
    private Integer page = 0;
    private Integer point = 0;
    private Integer rank = 0;
    private Integer danmaku = 0;
    private Integer share = 0;
    private Integer like = 0;
    private Integer dislike = 0;
    private String evaluation = "";

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
}
