package com.tilitili.common.manager;

import com.tilitili.common.mapper.ResourcesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class ResourcesManager {
    private final ResourcesMapper resourcesMapper;

    @Autowired
    public ResourcesManager(ResourcesMapper resourcesMapper) {
        this.resourcesMapper = resourcesMapper;
    }

    public Integer getIssueSupplement() {
        return 20;
//        return Integer.valueOf(resourcesMapper.getValueByType("ISSUE_SUPPLEMENT"));
    }

    public Integer getRecommendIssue() {
        return Integer.valueOf(resourcesMapper.getValueByType("RECOMMEND_ISSUE"));
    }

    public Boolean isForwardTempMessage() {
        String str = Optional.ofNullable(resourcesMapper.getValueByType("is_forward_temp_message")).orElse("").trim();
        return Objects.equals(str, "1");
    }

}
