package com.tilitili.common.entity;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Subscription {
	private Long id;
	private Date createTime;
	private Date updateTime;
	private Integer status;
	private Integer type;
	private String remark;
	private String value;
	private Long sendGroup;
	private String sendType;
}