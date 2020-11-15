package com.tilitili.common.manager;

import com.tilitili.common.entity.VideoPage;
import com.tilitili.common.mapper.VideoPageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VideoPageManager {

    private final VideoPageMapper videoPageMapper;

    @Autowired
    public VideoPageManager(VideoPageMapper videoPageMapper) {
        this.videoPageMapper = videoPageMapper;
    }

    public int updateOrInsert(VideoPage videoPage) {
        if (videoPageMapper.getByCidAndAv(videoPage.getCid(), videoPage.getAv()) == null) {
            return videoPageMapper.insert(videoPage);
        }else {
            return videoPageMapper.update(videoPage);
        }
    }

}
