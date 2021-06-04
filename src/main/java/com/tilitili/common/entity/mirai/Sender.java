package com.tilitili.common.entity.mirai;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Sender {
    private Long id;
    private String nickname;
    private String remark;
}
