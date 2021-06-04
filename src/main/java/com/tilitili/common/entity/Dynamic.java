package com.tilitili.common.entity;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Dynamic {
	private Long id;
	private Date createTime;
	private Date updateTime;
	private Integer status;
	private Integer type;
	private String remark;
	private String text;
	private Long externalId;
	private Integer externalType;
	private Long uid;
	private String pic;
}