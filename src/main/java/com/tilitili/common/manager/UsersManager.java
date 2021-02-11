package com.tilitili.common.manager;

import com.tilitili.common.entity.Users;
import com.tilitili.common.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsersManager {
    private final UsersMapper usersMapper;

    @Autowired
    public UsersManager(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    public Users getUsers(Users users) {
        if (users.getId() != null) {
            return usersMapper.getById(users.getId());
        } else if (users.getName() != null) {
            return usersMapper.getByName(users.getName());
        } else if (users.getPhone() != null) {
            return usersMapper.getByPhone(users.getPhone());
        } else if (users.getEmail() != null) {
            return usersMapper.getByEmail(users.getEmail());
        } else {
            return null;
        }
    }

}
