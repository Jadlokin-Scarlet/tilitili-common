package com.tilitili.common.mapper;

import com.tilitili.common.entity.VideoTag;
import com.tilitili.common.entity.query.VideoTagQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface VideoTagMapper {
    List<VideoTag> list(VideoTagQuery videoTagQuery);
    int count(VideoTagQuery videoTagQuery);
    int insert(VideoTag videoTag);
    int update(VideoTag videoTag);
}
