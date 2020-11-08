package com.tilitili.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class BatchTask {
    private Long id;
    private Integer status;
    private Integer type;
    private Date createTime;
    private Date updateTime;
    private String remark;
    private Integer reason;

    private Integer totalTaskNumber;
    private Integer waitTaskNumber;
    private Integer successTaskNumber;
    private Integer failTaskNumber;

    private List<Task> taskList;
}
