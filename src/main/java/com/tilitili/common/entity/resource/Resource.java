package com.tilitili.common.entity.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
    private Object value;
    private Object text;

    public Resource(Object text) {
        this.text = text;
        this.value = text;
    }
}
