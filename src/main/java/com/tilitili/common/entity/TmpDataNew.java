package com.tilitili.common.entity;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;

@Data
@Accessors(chain = true)
public class TmpDataNew {
	private Long av;
	private String bv;
	private Integer survival;
	private Long view;
	private Long danmaku;
	private Long reply;
	private Long favorite;
	private Long coin;
	private Long share;
	private Long page;
	private Integer copyright;
	private Long like;
	private Long dislike;
	private Long point;
	private Date collectTime;
}