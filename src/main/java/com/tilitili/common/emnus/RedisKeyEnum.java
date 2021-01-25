package com.tilitili.common.emnus;

public enum  RedisKeyEnum {
    // 存轮询作者的游标
    SPIDER_ALL_OWNER_REDIS_KEY("SPIDER_ALL_OWNER_REDIS_KEY")
    ;

    private String key;

    RedisKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
