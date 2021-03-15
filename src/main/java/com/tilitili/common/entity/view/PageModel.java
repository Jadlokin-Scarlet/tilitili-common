package com.tilitili.common.entity.view;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageModel<T> {
    @JsonView(BaseModel.BaseView.class)
    private Integer total;
    @JsonView(BaseModel.BaseView.class)
    private Integer pageSize;
    @JsonView(BaseModel.BaseView.class)
    private Integer current;
    @JsonView(BaseModel.BaseView.class)
    private Boolean hasMore;
    @JsonView(BaseModel.BaseView.class)
    private List<T> list;

    public static <T> BaseModel of(Integer total, Integer pageSize, Integer current, List<T> list) {
        boolean hasMore = pageSize * current < total;
        PageModel<T> pageModel = new PageModel<>(total, pageSize, current, hasMore, list);
        return new BaseModel("查询成功", true, pageModel);
    }

}
