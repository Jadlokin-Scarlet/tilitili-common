package com.tilitili.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class Users {
    private Long id;
    private String name;
    private String password;
    private String email;
    private String phone;
    private Integer status;
    private Integer type;
    private Date createTime;
    private Date updateTime;
    private Date lastLoginTime;
    private String face;
}
