package com.tilitili.common.entity.view;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class BaseModel {
    private String message;
    private Boolean success;
    private Object data;

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
}
