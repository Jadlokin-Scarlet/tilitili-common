package com.tilitili.common.mapper;

import com.tilitili.common.entity.VideoPage;
import com.tilitili.common.entity.query.VideoPageQuery;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface VideoPageMapper {

    List<VideoPage> list(VideoPageQuery videoPageQuery);
    int count(VideoPageQuery videoPageQuery);
    int insert(VideoPage videoPage);
    int update(VideoPage videoPage);

    @Select("select * from [page] where cid = #{cid} and av = #{av}")
    @ResultMap("VideoPageResultMap")
    VideoPage getByCidAndAv(Long cid, Long av);

}
