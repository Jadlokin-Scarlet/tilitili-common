package com.tilitili.common.mapper;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import com.tilitili.common.entity.Dynamic;

@Component
public interface DynamicMapper {
	Long insertDynamic(Dynamic dynamic);
	void updateDynamic(Dynamic dynamic);
	int countDynamicByCondition (Dynamic dynamic);
	List<Dynamic> listDynamicByCondition (Dynamic dynamic);

	@Select("select * from dynamic where external_id = #{externalId} and status != -1")
	Dynamic getByExternalId(Long externalId);
}