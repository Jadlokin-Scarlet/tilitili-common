package com.tilitili.common;

import com.tilitili.common.entity.Subscription;
import com.tilitili.common.mapper.SubscriptionMapper;
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
public class MainTest {
    @Autowired
    private SubscriptionMapper subscriptionMapper;

    @Test
    public void test() {
        subscriptionMapper.insertSubscription(new Subscription().setValue("123"));
    }
}
