package com.tilitili.common.manager;

import com.tilitili.common.StartApplication;
import com.tilitili.common.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartApplication.class)
@EnableAutoConfiguration
public class TaskManagerTest {

    @Autowired
    private TaskManager taskManager;
    @Autowired
    private RedisCache redisCache;

    @Test
    public void countByGroupStatus() {
//        Map<Integer, Integer> map = taskManager.countByGroupStatus(new TaskQuery().setBatchId(33L));
//        System.out.println(map);
        System.out.println(redisCache.get("test"));
    }
}