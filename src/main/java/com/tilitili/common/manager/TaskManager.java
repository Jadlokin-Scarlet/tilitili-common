package com.tilitili.common.manager;

import com.tilitili.common.emnus.TaskType;
import com.tilitili.common.entity.BatchTask;
import com.tilitili.common.entity.Task;
import com.tilitili.common.entity.dto.TaskStatusCount;
import com.tilitili.common.entity.message.TaskMessage;
import com.tilitili.common.entity.query.TaskQuery;
import com.tilitili.common.entity.view.SimpleTaskView;
import com.tilitili.common.mapper.BatchTaskMapper;
import com.tilitili.common.mapper.TaskMapper;
import com.tilitili.common.sender.TaskSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TaskManager {

    private final TaskMapper taskMapper;
    private final BatchTaskMapper batchTaskMapper;
    private final TaskSender taskSender;

    @Autowired
    public TaskManager(TaskMapper taskMapper, BatchTaskMapper batchTaskMapper, TaskSender taskSender) {
        this.taskMapper = taskMapper;
        this.batchTaskMapper = batchTaskMapper;
        this.taskSender = taskSender;
    }

    public Map<Integer, Integer> countByGroupStatus(TaskQuery taskQuery) {
        Map<Integer, Integer> result = new HashMap<>();
        List<TaskStatusCount> mapList = taskMapper.countByGroupStatus(taskQuery);
        mapList.forEach(taskStatusCount -> result.put(taskStatusCount.getStatus(), taskStatusCount.getNumber()));
        return result;
    }

    public void simpleSpiderVideo(SimpleTaskView simpleTaskView) {
        String value = simpleTaskView.getValue();
        BatchTask batchTask = new BatchTask().setType(TaskType.SpiderVideo.getValue()).setReason(simpleTaskView.getReason());
        batchSpiderVideo(batchTask, Collections.singletonList(value));
    }

    public void batchSpiderVideo(BatchTask batchTask, List<String> valueList) {
        batchTaskMapper.insert(batchTask);
        valueList.stream().parallel().forEach(value -> {
            Task task = new Task().setValue(value).setBatchId(batchTask.getId()).setType(batchTask.getType());
            taskMapper.insert(task);

            TaskMessage taskMessage = new TaskMessage().setValue(value).setId(task.getId()).setType(batchTask.getType()).setReason(batchTask.getReason());
            taskSender.sendTask(taskMessage);
        });
    }

}
