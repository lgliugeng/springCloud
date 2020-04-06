package com.liugeng.mq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqReceiver {

    @RabbitHandler
    @RabbitListener(queues = "hello")
    public void receive(String msg) throws Exception {
        System.out.println("MQ接受成功，消息：" + msg);
    }
}
