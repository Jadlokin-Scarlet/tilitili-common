package com.tilitili.common.manager;

import com.tilitili.common.entity.VideoInfo;
import com.tilitili.common.entity.dto.VideoInfoGroup;
import com.tilitili.common.entity.query.VideoInfoQuery;
import com.tilitili.common.mapper.VideoInfoMapper;
import com.tilitili.common.utils.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Map<String, Integer> groupByPubTime(VideoInfoQuery query) {
        return videoInfoMapper.groupByPubTime(query).stream().collect(Collectors.toMap(VideoInfoGroup::getTime, VideoInfoGroup::getNumber));
    }
}
