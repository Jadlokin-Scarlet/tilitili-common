package com.tilitili.common.mapper;

import com.tilitili.common.entity.VideoTag;
import com.tilitili.common.entity.VideoTagRelation;
import com.tilitili.common.entity.dto.TagRelationGroup;
import com.tilitili.common.entity.query.BaseQuery;
import com.tilitili.common.entity.query.VideoTagQuery;
import com.tilitili.common.entity.query.VideoTagRelationQuery;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface VideoTagRelationMapper {

    List<VideoTagRelation> list(VideoTagRelationQuery videoTagRelationQuery);
    int count(VideoTagRelationQuery videoTagRelationQuery);
    int insert(VideoTagRelation videoTagRelation);
    int update(VideoTagRelation videoTagRelation);

    int countVideoTag(VideoTagQuery query);
    List<VideoTag> listVideoTag(VideoTagQuery query);

    List<TagRelationGroup> groupByTag(VideoTagQuery query);

    @Select("select * from video_tag_relation where id = #{id}")
    @ResultMap("VideoTagRelationResultMap")
    VideoTagRelation getVideoTagRelationById(Long id);

    @Select("select * from video_tag_relation where tag_id = #{tagId} and av = #{av}")
    @ResultMap("VideoTagRelationResultMap")
    VideoTagRelation getVideoTagRelationByTagIdAndAv(Long tagId, Long av);
}
