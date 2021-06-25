package com.tilitili.common.entity.view.baidu;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TranslateBaseView {
    private String error_code;
    private String error_msg;
    private TranslateView data;
}
