package com.tilitili.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain=true)
public class VideoPage implements Serializable {
    private Long cid;

    private Long av;

    private String part;

    private Integer page;

    private String from;

    private Long duration;

    private String vid;

    private String weblink;

    private static final long serialVersionUID = 1L;
}