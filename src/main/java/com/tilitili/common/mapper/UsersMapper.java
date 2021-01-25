package com.tilitili.common.mapper;

import com.tilitili.common.entity.Users;
import com.tilitili.common.entity.query.UsersQuery;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UsersMapper {

    List<Users> list(UsersQuery UsersQuery);
    int count(UsersQuery UsersQuery);
    int insert(Users Users);
    int update(Users Users);

    @Select("select * from users where id = #{id}")
    Users getById(Long id);

    @Select("select * from users where name = #{name}")
    Users getByName(String name);

    @Select("select * from users where email = #{email}")
    Users getByEmail(String email);

    @Select("select * from users where phone = #{phone}")
    Users getByPhone(String phone);

}
