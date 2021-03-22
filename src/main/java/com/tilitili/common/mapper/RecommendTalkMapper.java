package com.tilitili.common.mapper;

import com.tilitili.common.entity.RecommendTalk;
import com.tilitili.common.entity.query.RecommendTalkQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RecommendTalkMapper {

    List<RecommendTalk> list(RecommendTalkQuery recommendTalkQuery);
    int count(RecommendTalkQuery recommendTalkQuery);
    int insert(RecommendTalk recommendTalk);
    int update(RecommendTalk recommendTalk);

}
