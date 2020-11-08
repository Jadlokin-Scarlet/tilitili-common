package com.tilitili.common.manager;

import com.tilitili.common.entity.dto.TaskStatusCount;
import com.tilitili.common.entity.query.TaskQuery;
import com.tilitili.common.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TaskManager {

    private final TaskMapper taskMapper;

    @Autowired
    public TaskManager(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    public Map<Integer, Integer> countByGroupStatus(TaskQuery taskQuery) {
        Map<Integer, Integer> result = new HashMap<>();
        List<TaskStatusCount> mapList = taskMapper.countByGroupStatus(taskQuery);
        mapList.forEach(taskStatusCount -> result.put(taskStatusCount.getStatus(), taskStatusCount.getNumber()));
        return result;
    }

}
