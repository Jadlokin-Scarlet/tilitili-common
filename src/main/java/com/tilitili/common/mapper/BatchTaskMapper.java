package com.tilitili.common.mapper;

import com.tilitili.common.entity.BatchTask;
import com.tilitili.common.entity.query.BatchTaskQuery;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BatchTaskMapper {
    
    List<BatchTask> list(BatchTaskQuery batchTaskQuery);
    int count(BatchTaskQuery batchTaskQuery);
    int insert(BatchTask batchTask);
    int update(BatchTask batchTask);

    @Select("select * from batch_task where id = #{id}")
    BatchTask getById(Long id);

    @Delete("delete batch_task where id = #{id}")
    int delete(Long id);

}
