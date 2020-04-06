package com.liugeng.mq;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(value = {StreanClient.class})
public class SinkReceiver {

    /**将方法注册到input通道监听消息处理*/
    @StreamListener(StreanClient.INPUT)
    public void receive(String payload){
        System.out.println("receive：" + payload);
    }
}
