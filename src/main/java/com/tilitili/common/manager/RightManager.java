package com.tilitili.common.manager;

import com.tilitili.common.entity.Right;
import com.tilitili.common.mapper.RightMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RightManager {

    private final RightMapper rightMapper;

    @Autowired
    public RightManager(RightMapper rightMapper) {
        this.rightMapper = rightMapper;
    }

    public int updateOrInsert(Right right) {
        if (rightMapper.getByAv(right.getAv()) == null) {
            return rightMapper.insert(right);
        }else {
            return rightMapper.update(right);
        }
    }

}
