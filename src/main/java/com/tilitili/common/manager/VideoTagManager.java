package com.tilitili.common.manager;

import com.tilitili.common.entity.VideoTag;
import com.tilitili.common.entity.VideoTagRelation;
import com.tilitili.common.entity.query.VideoTagQuery;
import com.tilitili.common.entity.query.VideoTagRelationQuery;
import com.tilitili.common.mapper.VideoTagRelationMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class VideoTagManager {

//    @Resource
//    private VideoTagRelationMapper videoTagRelationMapper;
//
//    public List<VideoTag> list(VideoTagQuery videoTagQuery) {
//        VideoTagRelationQuery videoTagRelationQuery = new VideoTagRelationQuery();
//        BeanUtils.copyProperties(videoTagQuery, videoTagRelationQuery);
//        List<VideoTagRelation> videoTagRelationList = videoTagRelationMapper.list(videoTagRelationQuery);
//        // key: av, value: List<Relation> -> videoTag
//        Map<Long, VideoTag> videoTagMap = videoTagRelationList.stream().collect(Collectors.groupingBy(
//                VideoTagRelation::getAv,
//                Collectors.collectingAndThen(Collectors.toList(), VideoTag::new)
//        ));
//        return new ArrayList<>(videoTagMap.values());
//    }
}
