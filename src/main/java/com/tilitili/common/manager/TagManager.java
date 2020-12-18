package com.tilitili.common.manager;

import com.tilitili.common.entity.Tag;
import com.tilitili.common.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TagManager {

    private final TagMapper tagMapper;

    @Autowired
    public TagManager(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    public int updateOrInsert(Tag tag) {
        if (tagMapper.getById(tag.getId()) == null) {
            return tagMapper.insert(tag);
        }else {
            return tagMapper.update(tag);
        }
    }

}
