package com.tilitili.common.entity;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;

@Data
@Accessors(chain = true)
public class BotCalendar {
	private Long id;
	private Date sendTime;
	private String text;
	private Date createTime;
	private Date updateTime;
	private Integer status;
	private Integer type;
	private String sendType;
	private Long sendQq;
	private Long sendGroup;
}