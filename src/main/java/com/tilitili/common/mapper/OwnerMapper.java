package com.tilitili.common.mapper;

import com.tilitili.common.entity.Owner;
import com.tilitili.common.entity.query.OwnerQuery;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OwnerMapper {
    List<Owner> list(OwnerQuery ownerQuery);
    int count(OwnerQuery ownerQuery);
    int insert(Owner owner);
    int update(Owner owner);

    @Select("select * from owner where uid = #{uid}")
    Owner getByUid(Long uid);
}