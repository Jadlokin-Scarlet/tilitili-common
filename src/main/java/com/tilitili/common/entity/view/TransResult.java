package com.tilitili.common.entity.view;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TransResult {
    private String src;
    private String dst;
}
