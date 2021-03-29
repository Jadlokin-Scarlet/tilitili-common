package com.tilitili.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class VideoInfo {
    private Long av;
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
    private Date createTime;
    private Date updateTime;
    private String externalOwner;
    private Boolean isCopyWarning;
    private String firstType;
}
