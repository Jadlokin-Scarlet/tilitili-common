package com.tilitili.common.entity.query;

import lombok.Getter;

@Getter
public class BaseQuery<T extends BaseQuery<T>> {
    private String sorter;
    private String sorted = "desc";
    private Integer start = 0;
    private Integer pageSize = 20;
    private Integer current = 1;
    private String subSorter;
    private String subSorted;

    public T setSorter(String sorter) {
        this.sorter = sorter;
        return (T) this;
    }

    public T setSorter(String sorter, String sorted) {
        this.sorter = sorter;
        this.sorted = sorted;
        return (T) this;
    }

    public T setSorted(String sorted) {
        this.sorted = sorted;
        return (T) this;
    }

    public T setStart(Integer start) {
        this.start = start;
        return (T) this;
    }

    public T setCurrent(Integer current) {
        this.current = current;
        return (T)this;
    }

    public T setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return (T)this;
    }

    public Integer getStart() {
        if (start == 0) {
            return (current - 1) * pageSize;
        }
        return start;
    }

    public Integer getCurrent() {
        if (current == 0) {
            return start / pageSize + 1;
        }
        return current;
    }

    public T setSubSorter(String subSorter) {
        this.subSorter = subSorter;
        return (T) this;
    }

    public T setSubSorted(String subSorted) {
        this.subSorted = subSorted;
        return (T) this;
    }

    public String getSubSorter() {
        return subSorter;
    }

    public String getSubSorted() {
        return subSorted;
    }
}
