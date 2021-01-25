package com.tilitili.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class Resources {
    private Long id;
    private Date createTime;
    private Date updateTime;
    private Integer status;
    private Integer type;
    private String value;
}
