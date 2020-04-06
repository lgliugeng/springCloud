package com.liugeng;

import com.liugeng.mq.StreanClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootStreamHelloApplicationTests {

    @Autowired
    private StreanClient streanClient;

    @Test
    public void contextLoads() {
        streanClient.output().send(MessageBuilder.withPayload("infos").build());
    }

}
