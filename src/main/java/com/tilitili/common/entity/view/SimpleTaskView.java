package com.tilitili.common.entity.view;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SimpleTaskView {
    private Long av;
    private Integer reason;
}
