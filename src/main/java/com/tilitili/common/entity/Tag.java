package com.tilitili.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class Tag {
    private Long id;
    private String name;
    private String cover;
    private String content;
    private Date createTime;
    private Date updateTime;
    private Date externalCreateTime;
    private Integer status;
    private Integer type;
    private Boolean isTouhouTag;

    //？？
    private String headCover;
    private String shortContent;
    private Integer externalType;
    private Integer state;
    private Integer isAtten;
    private Integer likes;
    private Integer hates;
    private Integer attribute;
    private Integer liked;
    private Integer hated;
    private Integer extraAttr;
    private String tagType;
    private Boolean isActivity;
    private String color;
    private Integer alpha;
    private Boolean isSeason;
    private Integer subscribedCount;
    private String archiveCount;
    private Integer featuredCount;
}
