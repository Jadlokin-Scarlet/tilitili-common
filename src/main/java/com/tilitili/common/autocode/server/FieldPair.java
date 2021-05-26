package com.tilitili.common.autocode.server;

import com.google.gson.Gson;

import java.util.List;

public class FieldPair {
    public String columnName;
    public String columnType;
    public String propertyName;
    public String propertyType;
    public String propertyClassName;
    public String displayName;
    public Boolean isPrimaryKey = false;
    public List<Filter> filters;
    public Boolean isIndex = false;


    public FieldPair(String columnName, String columnType, String propertyName, String propertyType, String propertyClassName) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.propertyClassName = propertyClassName;
    }

    public Boolean getIndex() {
        return isIndex;
    }

    public void setIndex(Boolean index) {
        isIndex = index;
    }
    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean getPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public String getPropertyClassName() {
        return propertyClassName;
    }

    public void setPropertyClassName(String propertyClassName) {
        this.propertyClassName = propertyClassName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
