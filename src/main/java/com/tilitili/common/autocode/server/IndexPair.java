package com.tilitili.common.autocode.server;

import com.google.gson.Gson;

import java.util.List;

public class IndexPair {
    public String indexName;
    public List<FieldPair> fieldPairList;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public List<FieldPair> getFieldPairList() {
        return fieldPairList;
    }

    public void setFieldPairList(List<FieldPair> fieldPairList) {
        this.fieldPairList = fieldPairList;
    }

    public String toString() {
        return new Gson().toJson(this);
    }

}
