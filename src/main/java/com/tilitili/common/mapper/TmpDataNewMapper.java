package com.tilitili.common.mapper;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import com.tilitili.common.entity.TmpDataNew;

@Component
public interface TmpDataNewMapper {
	Long insertTmpDataNew(TmpDataNew tmpDataNew);
	void updateTmpDataNew(TmpDataNew tmpDataNew);
	int countTmpDataNewByCondition (TmpDataNew tmpDataNew);
	List<TmpDataNew> listTmpDataNewByCondition (TmpDataNew tmpDataNew);

	@Select("select * from tmp_data_new where av = #{av}")
	TmpDataNew getTmpDataNewByAv(Long av);
}