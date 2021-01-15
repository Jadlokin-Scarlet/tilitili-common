package com.tilitili.common.mapper;

import com.tilitili.common.entity.Admin;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface AdminMapper {

    @Select("select * from admin where id = #{id}")
    Admin getById(Long id);

    @Select("select * from admin where user_name = #{userName} and status = 0")
    @ResultMap("adminResults")
    Admin getByName(String userName);

}
