package com.tilitili.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class RecommendTalk {
    private Integer id;
    private Integer type;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private String speaker;
    private String text;
    private String expression;
    private Integer issueId;
}
