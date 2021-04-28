package com.tilitili.common.entity.view;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
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
    @JsonView(BaseModel.BaseView.class)
    private Object data;

    public static <T> BaseModel of(Integer total, Integer pageSize, Integer current, List<T> list) {
        boolean hasMore = pageSize * current < total;
        PageModel<T> pageModel = new PageModel<T>().setTotal(total).setPageSize(pageSize).setCurrent(current).setHasMore(hasMore).setList(list);
        return new BaseModel("查询成功", true, pageModel);
    }

    public static <T> BaseModel of(Integer total, Integer pageSize, Integer current, List<T> list, Object data) {
        boolean hasMore = pageSize * current < total;
        PageModel<T> pageModel = new PageModel<T>().setTotal(total).setPageSize(pageSize).setCurrent(current).setHasMore(hasMore).setList(list).setData(data);
        return new BaseModel("查询成功", true, pageModel);
    }

}
