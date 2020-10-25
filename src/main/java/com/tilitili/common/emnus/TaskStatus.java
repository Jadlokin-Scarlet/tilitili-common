package com.tilitili.common.emnus;

public enum TaskStatus {
    DELETE(-1, "废弃"),
    WAIT(0, "待爬取"),
    SPIDER(1, "爬取中"),
    SUCCESS(2, "爬取成功"),
    FAIL(3, "爬取失败"),
    TIMEOUT(4, "爬取超时"),
    ;

    private TaskStatus(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    private Integer value;
    private String text;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
