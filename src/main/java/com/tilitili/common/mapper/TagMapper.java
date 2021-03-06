package com.tilitili.common.mapper;

import com.tilitili.common.entity.Tag;
import com.tilitili.common.entity.query.TagQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TagMapper {

    List<Tag> list(TagQuery tagQuery);
    int count(TagQuery tagQuery);
    int insert(Tag tag);
    int update(Tag tag);

    @Select("select * from [tag] where id = #{id}")
    @ResultMap("TagResultMap")
    Tag getById(Long id);

    @Select("select * from [tag] where id in (${idListStr})")
    List<Tag> listByIdListStr(@Param("idListStr") String idListStr);

    @Select("select tag.* from [tag] inner join video_tag_relation vtr on tag.id = vtr.tag_id where vtr.av = #{av} and vtr.status != -1 and tag.status != -1")
    List<Tag> listByAv(Long av);
}
