package com.tilitili.common.mapper;

import com.tilitili.common.entity.VideoTagRelation;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface VideoTagRelationMapper {

//    List<VideoTagRelation> list(VideoTagRelationQuery videoTagRelationQuery);
//    int count(VideoTagRelationQuery videoTagRelationQuery);
    int insert(VideoTagRelation videoTagRelation);
    int update(VideoTagRelation videoTagRelation);

    @Select("select * from video_tag_relation where id = #{id}")
    @ResultMap("VideoTagRelationResultMap")
    VideoTagRelation getVideoTagRelationById(Long id);

    @Select("select * from video_tag_relation where tag_id = #{tagId} and av = #{av}")
    @ResultMap("VideoTagRelationResultMap")
    VideoTagRelation getVideoTagRelationByTagIdAndAv(Long tagId, Long av);

}
