package com.tilitili.common.entity.mirai;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MessageChain {
    private String type;
    private String text;
    private String url;
}