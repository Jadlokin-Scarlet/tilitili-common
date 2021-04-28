package com.tilitili.common.mapper;

import com.tilitili.common.entity.VideoData;
import com.tilitili.common.entity.VideoInfo;
import com.tilitili.common.entity.dto.VideoDataGroup;
import com.tilitili.common.entity.query.VideoDataQuery;
import com.tilitili.common.entity.query.VideoInfoQuery;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface VideoDataMapper {
    List<VideoData> list(VideoDataQuery videoInfoQuery);
    int count(VideoDataQuery videoInfoQuery);
    int insert(VideoData videoData);
    int update(VideoData videoData);

    List<VideoDataGroup> groupByIssue(VideoDataQuery videoDataQuery);

    @Select("select * from video_data where av = #{av} and issue = #{issue}")
    @ResultMap("VideoDataResultMap")
    VideoData getByAvAndIssue(Long av, Integer issue);

    @Select("select max(issue) from video_data")
    Integer getNewIssue();

    @Select("select issue from video_data group by issue order by issue desc")
    List<Integer> listIssue();

    @Update("update video_data set rank = #{rank} where av = #{av} and issue = #{issue}")
    void updateRank(VideoData videoData);

    List<VideoData> randomRanked(VideoDataQuery videoDataQuery);
}
