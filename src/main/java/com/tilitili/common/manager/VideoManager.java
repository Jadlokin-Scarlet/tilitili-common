package com.tilitili.common.manager;

import com.tilitili.common.entity.Video;
import com.tilitili.common.entity.query.VideoQuery;
import com.tilitili.common.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VideoManager {

    private final VideoMapper videoMapper;

    @Autowired
    public VideoManager(VideoMapper videoMapper) {
        this.videoMapper = videoMapper;
    }

    public List<Video> listVideo(VideoQuery videoQuery) {
        return videoMapper.list(videoQuery);
    }

    public List<Video> listTopVideo(VideoQuery videoQuery) {
        return videoMapper.list(videoQuery.setSorter("point"));
    }
}
