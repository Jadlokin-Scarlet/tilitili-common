package com.tilitili.common.entity.view.baidu;

import com.tilitili.common.entity.view.TransResult;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class TranslateView {
    private String from;
    private String to;
    private List<TransResult> trans_result;
    private List<TransResult> content;
    private String sumSrc;
    private String sumDst;
    private String error_code;
    private String error_msg;
}
