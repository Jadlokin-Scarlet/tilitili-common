package com.tilitili.common.entity.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class TaskMessage {
    private Long id;
    private Long av;
    private Integer type;
}
