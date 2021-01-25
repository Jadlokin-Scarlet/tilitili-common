package com.tilitili.common.mapper;

import com.tilitili.common.entity.Resources;
import com.tilitili.common.entity.query.ResourcesQuery;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ResourcesMapper {

    List<Resources> list(ResourcesQuery resourcesQuery);
    int count(ResourcesQuery resourcesQuery);
    int insert(Resources resources);
    int update(Resources resources);

    @Select("select value from resources where type = #{type}")
    String getValueByType(String type);

    @Update("update resources set value = #{value} where type = #{type}")
    void updateValueByType(String type, Object value);
}
