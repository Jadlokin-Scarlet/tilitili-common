package com.tilitili.common.entity.mirai;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MiraiMessage {
    private String sendType;
    private String messageType;
    private String message;
    private String url;
    private String voiceId;
    private Long group;
    private Long qq;
}
