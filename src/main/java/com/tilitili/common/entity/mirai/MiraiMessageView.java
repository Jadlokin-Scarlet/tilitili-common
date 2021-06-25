package com.tilitili.common.entity.mirai;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class MiraiMessageView {
    private String type;
    private List<MessageChain> messageChain;
    private Sender sender;
}
