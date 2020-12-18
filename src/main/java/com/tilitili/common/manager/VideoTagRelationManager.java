package com.tilitili.common.manager;

import com.tilitili.common.entity.VideoTagRelation;
import com.tilitili.common.mapper.VideoTagRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VideoTagRelationManager {

    private final VideoTagRelationMapper videoTagRelationMapper;

    @Autowired
    public VideoTagRelationManager(VideoTagRelationMapper videoTagRelationMapper) {
        this.videoTagRelationMapper = videoTagRelationMapper;
    }

    public int updateOrInsert(VideoTagRelation videoTagRelation) {
        if (videoTagRelationMapper.getVideoTagRelationByTagIdAndAv(videoTagRelation.getTagId(), videoTagRelation.getAv()) == null) {
            return videoTagRelationMapper.insert(videoTagRelation);
        }else {
            return videoTagRelationMapper.update(videoTagRelation);
        }
    }

}
