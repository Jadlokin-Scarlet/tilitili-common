package com.tilitili.common.mapper;

import com.tilitili.common.entity.RecommendTalk;
import com.tilitili.common.entity.query.RecommendTalkQuery;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RecommendTalkMapper {

    List<RecommendTalk> list(RecommendTalkQuery recommendTalkQuery);
    int count(RecommendTalkQuery recommendTalkQuery);
    int insert(RecommendTalk recommendTalk);
    int update(RecommendTalk recommendTalk);

    @Update("update recommend_talk set status = -1 where issue_id = #{issueId} and type = #{type}")
    void batchDelete(Integer issueId, Integer type);

}
