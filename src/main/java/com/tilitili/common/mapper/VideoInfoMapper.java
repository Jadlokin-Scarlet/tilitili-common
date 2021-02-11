package com.tilitili.common.mapper;

import com.tilitili.common.entity.VideoInfo;
import com.tilitili.common.entity.dto.VideoInfoGroup;
import com.tilitili.common.entity.query.VideoInfoQuery;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface VideoInfoMapper {
    List<VideoInfo> list(VideoInfoQuery videoInfoQuery);
    int count(VideoInfoQuery videoInfoQuery);
    int insert(VideoInfo videoInfo);
    int update(VideoInfo videoInfo);

    @Select("select * from video_info where av = #{av}")
    @ResultMap("VideoInfoResultMap")
    VideoInfo getByAv(Long av);

    @Select("select * from video_info where status != 0")
    @ResultMap("VideoInfoResultMap")
    List<VideoInfo> listHiddenVideo();

    @Update("update video_info set external_owner = #{externalOwner} where av = #{av}")
    void updateExternalOwner(Long av, String externalOwner);

    List<VideoInfoGroup> groupByCreateDay(VideoInfoQuery query);
}
