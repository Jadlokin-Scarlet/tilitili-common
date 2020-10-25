package com.tilitili.common.mapper;

import com.tilitili.common.entity.Task;
import com.tilitili.common.entity.query.TaskQuery;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TaskMapper {

    List<Task> list(TaskQuery taskQuery);

    int count(TaskQuery taskQuery);

    int insert(Task task);

    int update(Task task);

}
