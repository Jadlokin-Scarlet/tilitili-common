package com.tilitili.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class Recommend {
    private Long id;
    private Long av;
    private String text;
    private String operator;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private Integer issueId;
    private Integer startTime;
    private Integer endTime;
    private Integer sortNum;

    //video info
    private String name;
    private String owner;
    private String externalOwner;
    private String pubTime;
    private String type;
    private String bv;
    private Integer videoStatus;

    //recommend video
    private String issueName;
    private Integer issue;

    //view
    private Integer startMinute;
    private Integer endMinute;
}
