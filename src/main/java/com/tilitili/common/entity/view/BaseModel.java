package com.tilitili.common.entity.view;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class BaseModel {
    @JsonView(BaseView.class)
    private String message;
    @JsonView(BaseView.class)
    private Boolean success;
    @JsonView(BaseView.class)
    private Object data;

    public interface BaseView {}

    public BaseModel(String message) {
        this.message = message;
        this.success = false;
    }

    public BaseModel(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public BaseModel(String message, boolean success, Object data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public static BaseModel success(Object data) {
        return new BaseModel("success", true, data);
    }

    public static BaseModel success() {
        return new BaseModel("success", true);
    }
}
