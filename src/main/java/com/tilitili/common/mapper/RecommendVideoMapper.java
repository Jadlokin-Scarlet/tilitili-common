package com.tilitili.common.mapper;

import com.tilitili.common.entity.RecommendVideo;
import com.tilitili.common.entity.query.RecommendVideoQuery;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RecommendVideoMapper {
    List<RecommendVideo> list(RecommendVideoQuery recommendVideoQuery);
    int count(RecommendVideoQuery recommendVideoQuery);
    int insert(RecommendVideo recommendVideo);
    int update(RecommendVideo recommendVideo);

    @Select("select * from recommend_video where id = #{id}")
    RecommendVideo getById(Integer id);

    @Select("select * from recommend_video where issue = #{issue}")
    RecommendVideo getByIssue(Integer issue);

    @Select("select top 1 * from recommend_video order by id desc")
    RecommendVideo getNew();

    @Select("select issue from recommend_video group by issue order by issue desc")
    List<Integer> listRecommendIssue();
}
