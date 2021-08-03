package com.tilitili.common.autocode.config;

public abstract class BaseConfig {
    public abstract String getTableName();
    public abstract String getReplaceName();
    public abstract Boolean updateDomain();
    public abstract String getReplaceDomainName();
    public abstract String getReplacePrimaryKey();
}
