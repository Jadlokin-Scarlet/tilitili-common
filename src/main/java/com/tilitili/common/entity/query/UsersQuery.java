package com.tilitili.common.entity.query;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UsersQuery extends BaseQuery<UsersQuery> {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Integer status;
    private Integer type;
}
