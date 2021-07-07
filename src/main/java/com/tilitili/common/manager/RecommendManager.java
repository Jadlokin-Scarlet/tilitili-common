package com.tilitili.common.manager;

import com.tilitili.common.entity.Recommend;
import com.tilitili.common.entity.query.RecommendQuery;
import com.tilitili.common.mapper.RecommendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecommendManager {
    private final RecommendMapper recommendMapper;

    @Autowired
    public RecommendManager(RecommendMapper recommendMapper) {
        this.recommendMapper = recommendMapper;
    }

    public int countUseRecommend(RecommendQuery query) {
        return recommendMapper.count(query.setType(0).setStatus(1));
    }

    public int countRecommendPool(RecommendQuery query) {
        return recommendMapper.count(query.setType(0).setStatus(0));
    }

    public int countSelfRecommend(RecommendQuery query) {
        return recommendMapper.count(query.setType(1).setStatus(1));
    }

    public int countSelfRecommendPool(RecommendQuery query) {
        return recommendMapper.count(query.setType(1).setStatus(0));
    }

    public List<Recommend> listUseRecommend(RecommendQuery query) {
        return recommendMapper.list(query.setType(0).setStatus(1));
    }

    public List<Recommend> listRecommendPool(RecommendQuery query) {
        return recommendMapper.list(query.setType(0).setStatus(0));
    }

    public List<Recommend> listSelfRecommend(RecommendQuery query) {
        return recommendMapper.list(query.setType(1).setStatus(1));
    }

    public List<Recommend> listSelfRecommendPool(RecommendQuery query) {
        return recommendMapper.list(query.setType(1).setStatus(0));
    }
}
