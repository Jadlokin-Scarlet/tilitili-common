package com.tilitili.common.manager;

import com.tilitili.common.mapper.ResourcesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResourcesManager {
    private final ResourcesMapper resourcesMapper;

    @Autowired
    public ResourcesManager(ResourcesMapper resourcesMapper) {
        this.resourcesMapper = resourcesMapper;
    }

    public Integer getIssueSupplement() {
        return Integer.valueOf(resourcesMapper.getValueByType("ISSUE_SUPPLEMENT"));
    }

}
