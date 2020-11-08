package com.tilitili.common.mapper;

import com.tilitili.common.entity.Right;
import com.tilitili.common.entity.query.RightQuery;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RightMapper {

    List<Right> list(RightQuery rightQuery);
    int count(RightQuery rightQuery);
    int insert(Right right);
    int update(Right right);

    @Select("select * from [right] where av = #{av}")
    @ResultMap("RightResultMap")
    Right getByAv(Long av);

}
