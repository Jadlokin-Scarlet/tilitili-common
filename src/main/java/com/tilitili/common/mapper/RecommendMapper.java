package com.tilitili.common.mapper;

import com.tilitili.common.entity.Recommend;
import com.tilitili.common.entity.query.RecommendQuery;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RecommendMapper {
    List<Recommend> list(RecommendQuery recommendQuery);
    int count(RecommendQuery recommendQuery);
    int insert(Recommend recommend);
    int update(Recommend recommend);

    @Select("select * from recommend where av = #{av} and status != -1")
    Recommend getByAv(Long av);

}
