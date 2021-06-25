package com.tilitili.common.manager;

import com.tilitili.common.entity.VideoData;
import com.tilitili.common.entity.VideoInfo;
import com.tilitili.common.entity.dto.VideoDataGroup;
import com.tilitili.common.entity.query.VideoDataQuery;
import com.tilitili.common.entity.query.VideoInfoQuery;
import com.tilitili.common.mapper.VideoDataMapper;
import com.tilitili.common.utils.Asserts;
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

    public List<VideoData> listDataFile(Integer issue) {
        return videoDataMapper.listDataFile(issue + resourcesManager.getIssueSupplement()).parallelStream()
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

    public VideoData getVideoNewDataByAv(Long av) {
        VideoDataQuery videoDataQuery = new VideoDataQuery().setAv(av).setSorter("issue", "desc").setPageSize(1);
        List<VideoData> videoDataList = this.list(videoDataQuery);
        if (videoDataList.isEmpty()) {
            return null;
        }
        Asserts.isTrue(videoDataList.size() == 1, "getVideoNewDataByAv ？？？");
        return videoDataList.get(0);
    }



    public VideoData getVideoNewDataByAvOrDefault(Long av) {
        VideoData videoData = getVideoNewDataByAv(av);
        if (videoData == null) {
            return new VideoData().setAv(av);
        }
        return videoData;
    }

    public List<VideoData> randomRanked(VideoDataQuery videoDataQuery) {
        VideoDataQuery newVideoDataQuery = new VideoDataQuery();
        if (videoDataQuery != null) {
            BeanUtils.copyProperties(videoDataQuery, newVideoDataQuery);
            if (videoDataQuery.getIssue() != null) {
                newVideoDataQuery.setIssue(videoDataQuery.getIssue() + resourcesManager.getIssueSupplement());
            }
        }
        return videoDataMapper.randomRanked(newVideoDataQuery).parallelStream()
                .map(videoData -> videoData.setIssue(videoData.getIssue() - resourcesManager.getIssueSupplement()))
                .collect(Collectors.toList());
    }

    public void clearRank(Integer issue) {
        videoDataMapper.clearRank(issue + resourcesManager.getIssueSupplement());
    }
}
