package com.tilitili.common.manager;

import com.tilitili.common.entity.VideoData;
import com.tilitili.common.entity.dto.VideoDataGroup;
import com.tilitili.common.entity.query.VideoDataQuery;
import com.tilitili.common.entity.query.VideoInfoQuery;
import com.tilitili.common.mapper.VideoDataMapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VideoDataManager {
    
    private final VideoDataMapper videoDataMapper;
    private final ResourcesManager resourcesManager;

    @Autowired
    public VideoDataManager(VideoDataMapper videoDataMapper, ResourcesManager resourcesManager) {
        this.videoDataMapper = videoDataMapper;
        this.resourcesManager = resourcesManager;
    }


    public int updateOrInsert(VideoData videoData) {
        if (getByAvAndIssue(videoData.getAv(), videoData.getIssue()) == null) {
            return insert(videoData);
        }else {
            return update(videoData);
        }
    }

    public VideoData getOrDefault(Long av, Integer issue) {
        VideoData videoData = getByAvAndIssue(av, issue);
        if (videoData == null) {
            return new VideoData().setAv(av).setIssue(issue);
        }
        return videoData;
    }


    public List<VideoData> list(VideoDataQuery videoDataQuery) {
        VideoDataQuery newVideoDataQuery = new VideoDataQuery();
        if (videoDataQuery != null) {
            BeanUtils.copyProperties(videoDataQuery, newVideoDataQuery);
            if (videoDataQuery.getIssue() != null) {
                newVideoDataQuery.setIssue(videoDataQuery.getIssue() + resourcesManager.getIssueSupplement());
            }
        }
        return videoDataMapper.list(newVideoDataQuery).parallelStream()
                .map(videoData -> videoData.setIssue(videoData.getIssue() - resourcesManager.getIssueSupplement()))
                .collect(Collectors.toList());
    }

    public int count(VideoDataQuery videoDataQuery) {
        VideoDataQuery newVideoDataQuery = new VideoDataQuery();
        if (videoDataQuery != null) {
            BeanUtils.copyProperties(videoDataQuery, newVideoDataQuery);
            if (videoDataQuery.getIssue() != null) {
                newVideoDataQuery.setIssue(videoDataQuery.getIssue() + resourcesManager.getIssueSupplement());
            }
        }
        return videoDataMapper.count(newVideoDataQuery);
    }

    @Cacheable(value = "videoDataGroup", key = "#videoDataQuery.pageSize")
    public List<VideoDataGroup> groupByIssue(VideoDataQuery videoDataQuery) {
        VideoDataQuery newVideoDataQuery = new VideoDataQuery();
        if (videoDataQuery != null) {
            BeanUtils.copyProperties(videoDataQuery, newVideoDataQuery);
            if (videoDataQuery.getIssue() != null) {
                newVideoDataQuery.setIssue(videoDataQuery.getIssue() + resourcesManager.getIssueSupplement());
            }
        }
        return videoDataMapper.groupByIssue(newVideoDataQuery).parallelStream().peek(videoDataGroup ->
                videoDataGroup.setIssue(videoDataGroup.getIssue() - resourcesManager.getIssueSupplement())
        ).collect(Collectors.toList());
    }

    public int insert(VideoData videoData) {
        if (videoData != null && videoData.getIssue() != null) {
            videoData.setIssue(videoData.getIssue() + resourcesManager.getIssueSupplement());
        }
        return videoDataMapper.insert(videoData);
    }

    public int update(VideoData videoData) {
        if (videoData != null && videoData.getIssue() != null) {
            videoData.setIssue(videoData.getIssue() + resourcesManager.getIssueSupplement());
        }
        return videoDataMapper.update(videoData);
    }

    public VideoData getByAvAndIssue(Long av, Integer issue) {
        VideoData videoData = videoDataMapper.getByAvAndIssue(av, issue + resourcesManager.getIssueSupplement());
        if (videoData == null) {
            return null;
        }
        return videoData.setIssue(videoData.getIssue() - resourcesManager.getIssueSupplement());
    }

    public Integer getNewIssue() {
        return videoDataMapper.getNewIssue() - resourcesManager.getIssueSupplement();
    }

    public List<Integer> listIssue() {
        return videoDataMapper.listIssue().parallelStream()
                .map(issue -> issue - resourcesManager.getIssueSupplement())
                .collect(Collectors.toList());
    }

    public void updateRank(VideoData videoData) {
        if (videoData != null && videoData.getIssue() != null) {
            videoData.setIssue(videoData.getIssue() + resourcesManager.getIssueSupplement());
        }
        videoDataMapper.updateRank(videoData);
    }
}
