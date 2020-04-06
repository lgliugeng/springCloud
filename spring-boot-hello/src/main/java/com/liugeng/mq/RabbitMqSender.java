package com.liugeng.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Component
@Service
public class RabbitMqSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendHello(){
        String context = "hello" + new Date();
        amqpTemplate.convertAndSend("hello",context);
        System.out.println("MQ发送成功");
    }
}
