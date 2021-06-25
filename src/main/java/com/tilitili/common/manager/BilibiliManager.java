package com.tilitili.common.manager;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.tilitili.common.entity.view.BaseView;
import com.tilitili.common.entity.view.dynamic.Card;
import com.tilitili.common.entity.view.dynamic.DynamicPageView;
import com.tilitili.common.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class BilibiliManager {
    public List<Card> getDynamic(Long uid) {
        String url = String.format("https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/space_history?offset_dynamic_id=0&host_uid=%s",uid);
        String result = HttpClientUtil.httpGet(url);
        log.info("url={}, result={}",url,result);
        BaseView<DynamicPageView> json = new Gson().fromJson(result, new TypeToken<BaseView<DynamicPageView>>() {}.getType());
        if (json != null) {
            return json.data.cards;
        } else {
            return Collections.emptyList();
        }
    }
}
