package com.tilitili.common.mapper;

import com.tilitili.common.entity.BatchTask;
import com.tilitili.common.entity.query.BatchTaskQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BatchTaskMapper {
    
    List<BatchTask> list(BatchTaskQuery batchTaskQuery);
//    List<BatchTask> listWithJoin(BatchTaskQuery batchTaskQuery);

    int count(BatchTaskQuery batchTaskQuery);

    int insert(BatchTask batchTask);

    int update(BatchTask batchTask);



}
