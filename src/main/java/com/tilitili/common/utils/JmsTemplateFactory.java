package com.tilitili.common.utils;

import com.tilitili.common.emnus.TaskReason;
import com.tilitili.common.emnus.TaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;
import java.util.HashMap;
import java.util.Map;

import static com.tilitili.common.emnus.TaskReason.RE_SPIDER_All_VIDEO;
import static com.tilitili.common.emnus.TaskType.*;

@Component
public class JmsTemplateFactory {
    private final ConnectionFactory connectionFactory;
    private final MessageConverter messageConverter;
    private final Map<String, JmsTemplate> map;

    @Autowired
    public JmsTemplateFactory(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        this.connectionFactory = connectionFactory;
        this.messageConverter = messageConverter;
        map = new HashMap<>();
        updateJmsTemplate(null, null, 5, 7200000L);

        updateJmsTemplate(SpiderVideo, null, 3, null);
        updateJmsTemplate(AutoBatchSpiderVideo, null, 8, null);

        updateJmsTemplate(BatchSpiderVideo, RE_SPIDER_All_VIDEO, 2, 0L);
    }

    public void updateJmsTemplate(TaskType batchSpiderType, TaskReason batchSpiderReason, Integer priority, Long timeToLive) {
        JmsTemplate jmsTemplate = getJmsTemplate(batchSpiderType, batchSpiderReason);
        if (priority != null) {
            jmsTemplate.setPriority(priority);
        }
        if (timeToLive != null) {
            jmsTemplate.setTimeToLive(timeToLive);
        }
    }

    public JmsTemplate getJmsTemplate(TaskType batchSpiderType, TaskReason batchSpiderReason) {
        String typeKey = batchSpiderType == null? "*": batchSpiderType.value.toString();
        String reasonKey = batchSpiderReason == null? "*": batchSpiderReason.value.toString();
        String key = typeKey + "_" + reasonKey;
        if (! map.containsKey(key)) {
            JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
            jmsTemplate.setMessageConverter(messageConverter);
            jmsTemplate.setExplicitQosEnabled(true);

            if (batchSpiderReason != null) {
                jmsTemplate.setDefaultDestinationName(batchSpiderReason.destination);
            }

            copyDefaultQos(jmsTemplate, map.get("*_*"));
            copyDefaultQos(jmsTemplate, map.get(typeKey + "_*"));
            copyDefaultQos(jmsTemplate, map.get("*_" + reasonKey));

            map.put(key, jmsTemplate);
        }
        return map.get(key);
    }

    public JmsTemplate getJmsTemplate(Integer batchSpiderType, Integer batchSpiderReason) {
        return getJmsTemplate(TaskType.getByValue(batchSpiderType), TaskReason.getByValue(batchSpiderReason));
    }

    private void copyDefaultQos(JmsTemplate target, JmsTemplate source) {
        if (source == null) {
            return;
        }
        if (source.getPriority() != 4) {
            target.setPriority(source.getPriority());
        }
        if (source.getTimeToLive() != 0) {
            target.setTimeToLive(source.getTimeToLive());
        }
    }

}
