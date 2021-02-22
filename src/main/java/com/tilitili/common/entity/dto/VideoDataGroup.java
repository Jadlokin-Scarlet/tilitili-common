package com.tilitili.common.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class VideoDataGroup implements Serializable {
    private Integer view;
    private Integer coin;
    private Integer reply;
    private Integer favorite;
    private Integer issue;
}
