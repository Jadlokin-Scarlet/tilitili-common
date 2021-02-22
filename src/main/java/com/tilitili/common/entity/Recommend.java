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
    private Integer issue;

    //video info
    private String name;
    private String owner;
    private String externalOwner;
    private String pubTime;
    private String type;
}
