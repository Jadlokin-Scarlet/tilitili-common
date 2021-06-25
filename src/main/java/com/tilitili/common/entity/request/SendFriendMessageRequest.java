package com.tilitili.common.entity.request;

import com.tilitili.common.entity.mirai.MessageChain;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class SendFriendMessageRequest {
    private String sessionKey;
    private Long target;
    private Long group;
    private Long qq;
    private List<MessageChain> messageChain;
}