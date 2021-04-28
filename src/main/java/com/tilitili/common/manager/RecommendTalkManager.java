package com.tilitili.common.manager;

import com.tilitili.common.entity.RecommendTalk;
import com.tilitili.common.mapper.RecommendTalkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class RecommendTalkManager {

    private final RecommendTalkMapper recommendTalkMapper;

    @Autowired
    public RecommendTalkManager(RecommendTalkMapper recommendTalkMapper) {
        this.recommendTalkMapper = recommendTalkMapper;
    }

    @Transactional
    public void batchDeleteAndAdd(Integer issueId, Integer type, List<RecommendTalk> recommendTalkList) {
        recommendTalkMapper.batchDelete(issueId, type);
        recommendTalkList.forEach(recommendTalkMapper::insert);
    }
}
