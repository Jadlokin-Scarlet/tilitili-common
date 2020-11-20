package com.tilitili.common.sender;

import com.tilitili.common.emnus.TaskReason;
import com.tilitili.common.emnus.TaskType;
import com.tilitili.common.entity.message.TaskMessage;
import com.tilitili.common.utils.JmsTemplateFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpiderVideoViewTaskSender {

    private final JmsTemplateFactory jmsTemplateFactory;

    @Autowired
    public SpiderVideoViewTaskSender(JmsTemplateFactory jmsTemplateFactory) {
        this.jmsTemplateFactory = jmsTemplateFactory;
    }

    public void sendSpiderVideo(TaskMessage taskMessage) {
        JmsTemplate jmsTemplate = jmsTemplateFactory.getJmsTemplate(taskMessage.getType(), taskMessage.getReason());
        log.info("send task to mq{}_{}_{}: {}", jmsTemplate.getDefaultDestinationName(), jmsTemplate.getPriority(), jmsTemplate.getTimeToLive(), taskMessage);
        jmsTemplate.convertAndSend(taskMessage);
    }

}
