package com.tilitili.common.manager;

import com.tilitili.common.entity.VideoData;
import com.tilitili.common.mapper.VideoDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VideoDataManager {
    
    private final VideoDataMapper videoDataMapper;

    @Autowired
    public VideoDataManager(VideoDataMapper videoDataMapper) {
        this.videoDataMapper = videoDataMapper;
    }

    public int updateOrInsert(VideoData videoData) {
        if (videoDataMapper.getByAvAndIssue(videoData.getAv(), videoData.getIssue()) == null) {
            return videoDataMapper.insert(videoData);
        }else {
            return videoDataMapper.update(videoData);
        }
    }
    
}
