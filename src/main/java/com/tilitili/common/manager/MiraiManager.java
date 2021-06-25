package com.tilitili.common.manager;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tilitili.common.entity.mirai.MessageChain;
import com.tilitili.common.entity.mirai.MiraiMessage;
import com.tilitili.common.entity.request.SendFriendMessageRequest;
import com.tilitili.common.utils.Asserts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.net.SocketException;
import java.util.*;

import static com.tilitili.common.utils.HttpClientUtil.httpPost;

@Slf4j
@Component
public class MiraiManager {
    @Value("${mirai.bot-qq}")
    private String BOT_QQ;
    @Value("${mirai.friend-qq}")
    private Long FRIEND_QQ;
    @Value("${mirai.auth-key}")
    private String AUTH_KEY;
    @Value("${mirai.base-url}")
    private String BASE_URL;
    @Value("${mirai.group}")
    private Long group;

    private final Gson gson = new Gson();

    private String post(String api, Object data) {
        return httpPost(BASE_URL + api, gson.toJson(data));
    }

    @Retryable(value= {SocketException.class},maxAttempts = 2)
    public String auth() {
        Map<String, String> data = ImmutableMap.of("authKey", AUTH_KEY);
        String result = post("auth", data);
        Map<String, String> resultMap = gson.fromJson(result, new TypeToken<Map<String, String>>(){}.getType());
        Asserts.notNull(resultMap, "请求认证失败");
        String session = resultMap.get("session");
        Asserts.notBlank(session, "认证失败");
        return session;
    }

    @Retryable(value= {SocketException.class},maxAttempts = 2)
    public void verify(String session) {
        Map<String, String> data = ImmutableMap.of("sessionKey", session, "qq", BOT_QQ);
        String result = post("verify", data);
        Map<String, String> resultMap = gson.fromJson(result, new TypeToken<Map<String, String>>(){}.getType());
        Asserts.notNull(resultMap, "请求绑定失败");
        Asserts.isTrue(Objects.equals(resultMap.get("msg"), "success"), "绑定失败");
    }

    @Retryable(value= {SocketException.class},maxAttempts = 2)
    public void release(String session) {
        Map<String, String> data = ImmutableMap.of("sessionKey", session, "qq", BOT_QQ);
        String result = post("release", data);
        Map<String, String> resultMap = gson.fromJson(result, new TypeToken<Map<String, String>>(){}.getType());
        Asserts.isTrue(Objects.equals(resultMap.get("msg"), "success"), "解绑失败");
    }

    @Retryable(value= {SocketException.class},maxAttempts = 2)
    public Integer sendMessage(MiraiMessage miraiMessage) {
        String sendType = miraiMessage.getSendType();
        String messageType = miraiMessage.getMessageType();
        String message = miraiMessage.getMessage();
        String url = miraiMessage.getUrl();
        Long group = miraiMessage.getGroup();
        Long qq = miraiMessage.getQq();

        String session = this.auth();
        this.verify(session);

        SendFriendMessageRequest request = new SendFriendMessageRequest();
        request.setSessionKey(session);

        switch (messageType) {
            case "Plain": request.setMessageChain(Collections.singletonList(new MessageChain().setType("Plain").setText(message)));break;
            case "Image": request.setMessageChain(Collections.singletonList(new MessageChain().setType("Image").setUrl(url)));break;
            case "ImageText": request.setMessageChain(Arrays.asList(new MessageChain().setType("Plain").setText(message),new MessageChain().setType("Image").setUrl(url)));break;
            default: request.setMessageChain(Collections.emptyList());break;
        }

        String result;
        switch (sendType) {
            case "friend": result = post("sendFriendMessage", request.setTarget(qq)); break;
            case "group": result = post("sendGroupMessage", request.setTarget(group)); break;
            case "temp": result = post("sendTempMessage", request.setGroup(group).setQq(qq)); break;
            default: result = null;
        }
        Asserts.notBlank(result, "获取数据失败");

        Map<String, String> resultMap = gson.fromJson(result, new TypeToken<Map<String, String>>(){}.getType());
        Asserts.notNull(request, "解析数据失败");
        Asserts.isTrue(Objects.equals(resultMap.get("msg"), "success"), "发送消息失败");

        this.release(session);
        return Integer.parseInt(resultMap.get("messageId"));
    }

    @Retryable(value= {SocketException.class},maxAttempts = 2)
    public Integer sendFriendMessage(String type, String message) {
        return sendFriendMessage(type, message, FRIEND_QQ);
    }

    @Retryable(value= {SocketException.class},maxAttempts = 2)
    public Integer sendFriendMessage(String type, String message, Long qq) {
        return sendMessage(new MiraiMessage().setSendType("friend").setMessageType(type).setMessage(message).setQq(qq));
    }

    @Retryable(value= {SocketException.class},maxAttempts = 2)
    public Integer sendGroupMessage(String type, String message) {
        return sendGroupMessage(type, message, group);
    }

    @Retryable(value= {SocketException.class},maxAttempts = 2)
    public Integer sendGroupMessage(String type, String message, Long group) {
        return sendMessage(new MiraiMessage().setSendType("group").setMessageType(type).setMessage(message).setGroup(group));
    }

    @Retryable(value= {SocketException.class},maxAttempts = 2)
    public Integer sendTempMessage(String type, String message, Long group, Long qq) {
        return sendMessage(new MiraiMessage().setSendType("temp").setMessageType(type).setMessage(message).setGroup(group).setQq(qq));
    }

}
