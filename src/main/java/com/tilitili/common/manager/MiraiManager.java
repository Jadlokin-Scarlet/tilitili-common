package com.tilitili.common.manager;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tilitili.common.entity.Recommend;
import com.tilitili.common.entity.RecommendVideo;
import com.tilitili.common.entity.mirai.MessageChain;
import com.tilitili.common.entity.mirai.MiraiMessage;
import com.tilitili.common.entity.request.SendFriendMessageRequest;
import com.tilitili.common.mapper.RecommendMapper;
import com.tilitili.common.mapper.RecommendVideoMapper;
import com.tilitili.common.utils.Asserts;
import com.tilitili.common.utils.BilibiliUtil;
import com.tilitili.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.tilitili.common.utils.HttpClientUtil.httpPost;

@Slf4j
@Component
public class MiraiManager {
    @Value("${mirai.bot-qq}")
    private String BOT_QQ;
    @Value("${mirai.auth-key}")
    private String AUTH_KEY;
    @Value("${mirai.base-url}")
    private String BASE_URL;
    @Value("${mirai.group}")
    private Long group;

    private final Gson gson = new Gson();

    private String post(String api, Object data) {
        return httpPost(BASE_URL + api, gson.toJson(data), null, null, null);
    }

    public String auth() {
        Map<String, String> data = ImmutableMap.of("authKey", AUTH_KEY);
        String result = post("auth", data);
        Map<String, String> resultMap = gson.fromJson(result, new TypeToken<Map<String, String>>(){}.getType());
        String session = resultMap.get("session");
        Asserts.notBlank(session, "绑定失败");
        return session;
    }

    public void verify(String session) {
        Map<String, String> data = ImmutableMap.of("sessionKey", session, "qq", BOT_QQ);
        String result = post("verify", data);
        Map<String, String> resultMap = gson.fromJson(result, new TypeToken<Map<String, String>>(){}.getType());
        Asserts.isTrue(Objects.equals(resultMap.get("msg"), "success"), "绑定失败");
    }

    public void release(String session) {
        Map<String, String> data = ImmutableMap.of("sessionKey", session, "qq", BOT_QQ);
        String result = post("release", data);
        Map<String, String> resultMap = gson.fromJson(result, new TypeToken<Map<String, String>>(){}.getType());
        Asserts.isTrue(Objects.equals(resultMap.get("msg"), "success"), "解绑失败");
    }

    public Integer sendFriendMessage(String type, String message) {
        return sendFriendMessage(type, message);
    }

    public Integer sendFriendMessage(String type, String message, Long qq) {
        String session = this.auth();
        this.verify(session);

        SendFriendMessageRequest request = new SendFriendMessageRequest();
        request.setSessionKey(session);
        request.setTarget(545459363L);

        MessageChain messageChain = new MessageChain().setType(type);
        if (Objects.equals(type, "Plain")) {
            messageChain.setText(message);
        }else if (Objects.equals(type, "Image")) {
            messageChain.setUrl(message);
        }else {
            Asserts.isTrue(false, "不支持的类型");
        }
        request.setMessageChain(Arrays.asList(messageChain));

        String result = post("sendFriendMessage", request);
        Map<String, String> resultMap = gson.fromJson(result, new TypeToken<Map<String, String>>(){}.getType());
        Asserts.isTrue(Objects.equals(resultMap.get("msg"), "success"), "发送消息失败, " + resultMap.get("msg"));

        this.release(session);
        return Integer.parseInt(resultMap.get("messageId"));
    }
    public Integer sendGroupMessage(String type, String message) {
        return sendGroupMessage(type, message, group);
    }

    public Integer sendGroupMessage(String type, String message, Long group) {
        String session = this.auth();
        this.verify(session);

        SendFriendMessageRequest request = new SendFriendMessageRequest();
        request.setSessionKey(session);
        request.setGroup(group);

        MessageChain messageChain = new MessageChain().setType(type);
        if (Objects.equals(type, "Plain")) {
            messageChain.setText(message);
        }else if (Objects.equals(type, "Image")) {
            messageChain.setUrl(message);
        }else {
            Asserts.isTrue(false, "不支持的类型");
        }
        request.setMessageChain(Arrays.asList(messageChain));

        String result = post("sendGroupMessage", request);
        Map<String, String> resultMap = gson.fromJson(result, new TypeToken<Map<String, String>>(){}.getType());
        Asserts.isTrue(Objects.equals(resultMap.get("msg"), "success"), "发送消息失败");

        this.release(session);
        return Integer.parseInt(resultMap.get("messageId"));
    }

}
