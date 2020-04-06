package com.liugeng.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface StreanClient {

    String INPUT = "msg_input";

    String OUTPUT = "msg_output";

    @Input(StreanClient.INPUT)
    SubscribableChannel input();

    @Output(StreanClient.OUTPUT)
    MessageChannel output();

}
