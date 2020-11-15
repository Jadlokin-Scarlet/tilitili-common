package com.tilitili.common.manager;

import com.tilitili.common.entity.Owner;
import com.tilitili.common.mapper.OwnerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OwnerManager {

    private final OwnerMapper ownerMapper;

    @Autowired
    public OwnerManager(OwnerMapper ownerMapper) {
        this.ownerMapper = ownerMapper;
    }

    public int updateOrInsert(Owner owner) {
        if (ownerMapper.getByUid(owner.getUid()) == null) {
            return ownerMapper.insert(owner);
        }else {
            return ownerMapper.update(owner);
        }
    }

}
