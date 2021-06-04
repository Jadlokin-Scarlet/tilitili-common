package com.tilitili.common.entity;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;

@Data
@Accessors(chain = true)
public class ViewHistory {
	private String id;
	private String userId;
	private String av;
	private String status;
	private String type;
	private Date createTime;
	private Date updateTime;
	private String remark;
}