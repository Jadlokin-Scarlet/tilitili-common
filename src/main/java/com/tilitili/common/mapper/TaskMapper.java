package com.tilitili.common.mapper;

import com.tilitili.common.entity.Task;
import com.tilitili.common.entity.dto.TaskStatusCount;
import com.tilitili.common.entity.query.TaskQuery;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface TaskMapper {

    List<Task> list(TaskQuery taskQuery);
    int count(TaskQuery taskQuery);
    int insert(Task task);
    int update(Task task);

    @Select("select * from task where id = #{id}")
    Task getById(Long id);

    @Delete("delete task where batch_id = #{batchId}")
    int deleteByBatchId(Long batchId);

    List<TaskStatusCount> countByGroupStatus(TaskQuery taskQuery);

    @Update("update [task] set [status] = #{toStatus} where id = #{id} and [status] = #{fromStatus}")
    int updateStatusById(Long id, Integer fromStatus, Integer toStatus);

    @Update("update [task] set [status] = #{toStatus}, remark = #{remark} where id = #{id} and [status] = #{fromStatus}")
    int updateStatusAndRemarkById(Long id, Integer fromStatus, Integer toStatus, String remark);
}
