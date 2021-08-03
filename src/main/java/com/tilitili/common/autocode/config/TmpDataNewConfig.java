package com.tilitili.common.autocode.config;

import org.springframework.stereotype.Component;

@Component
public class TmpDataNewConfig extends BaseConfig{

    @Override
    public String getTableName() {
        return "tmp_data_new";
    }

    @Override
    public String getReplaceName() {
        return null;
    }

    @Override
    public Boolean updateDomain() {
        return true;
    }

    @Override
    public String getReplaceDomainName() {
        return null;
    }

    @Override
    public String getReplacePrimaryKey() {
        return "av";
    }
}
