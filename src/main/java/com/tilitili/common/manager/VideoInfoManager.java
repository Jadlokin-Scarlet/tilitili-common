package com.tilitili.common.manager;

import com.tilitili.common.entity.VideoInfo;
import com.tilitili.common.mapper.VideoInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VideoInfoManager {

    private final VideoInfoMapper videoInfoMapper;

    @Autowired
    public VideoInfoManager(VideoInfoMapper videoInfoMapper) {
        this.videoInfoMapper = videoInfoMapper;
    }

    public int updateOrInsert(VideoInfo videoInfo) {
        if (videoInfoMapper.getByAv(videoInfo.getAv()) == null) {
            return videoInfoMapper.insert(videoInfo);
        }else {
            return videoInfoMapper.update(videoInfo);
        }
    }

}
