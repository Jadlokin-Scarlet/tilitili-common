package com.tilitili.common.autocode.config;

public class VideoDataRollConfig extends BaseConfig {
    @Override
    public String getTableName() {
        return "video_data_roll";
    }

    @Override
    public String getReplaceName() {
        return null;
    }

    @Override
    public Boolean updateDomain() {
        return false;
    }

    @Override
    public String getReplaceDomainName() {
        return null;
    }
}
