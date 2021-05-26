package com.tilitili.common.entity.request;

import lombok.Data;

import java.util.List;

@Data
public class SendFriendMessageRequest {
    private String sessionKey;
    private Long target;
    private Long group;
    private List<MessageChain> messageChain;
}