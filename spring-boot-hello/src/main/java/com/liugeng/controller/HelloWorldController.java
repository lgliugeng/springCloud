package com.liugeng.controller;

import com.liugeng.dto.UserDto;
import com.liugeng.mq.RabbitMqSender;
import com.liugeng.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RefreshScope
public class HelloWorldController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private HelloService helloService;

    /**客户端*/
    @Autowired
    private DiscoveryClient client;

    @Autowired
    private RabbitMqSender sender;

    @Value("${name}")
    private String name;

    @RequestMapping(value = "/index")
    public String index() {
        List<String> stringList = client.getServices();
        int sleepTime = new Random().nextInt(3000);
        logger.info("模拟逻辑休眠：" + sleepTime + "毫秒");
        try {
            // 断路器超时时间为2000毫秒
            Thread.sleep(sleepTime);
        }catch (Exception e){e.printStackTrace();}
        stringList.forEach(s -> {
            List<ServiceInstance> instances = client.getInstances(s);
            instances.forEach(serviceInstance -> {
                logger.info("访问/index的应用信息：Host:" + serviceInstance.getHost() + "ServiceId:" + serviceInstance.getServiceId());
            });
        });
        return "Hello World" + name;
    }

    @RequestMapping(value = "/send")
    public void send() {
        System.out.println("开始发送MQ");
        sender.sendHello();
    }



    /**
     * 返回集合（测试Hystrix请求合并）
     *
     * @param id

     * @return    java.util.List<java.lang.String>
     * @author    liugeng
     * @date      2020/3/16 16:57
    */
    @RequestMapping(value = "/indexs/{id}")
    public String indexs(@PathVariable("id") String id) {
        return id;
    }

    @RequestMapping(value = "/helloUserService" ,method = RequestMethod.POST)
    public String helloUserService(@RequestBody UserDto userDto){
        return helloService.helloUserService(userDto);
    }

    @RequestMapping(value = "/addUserService" ,method = RequestMethod.GET)
    public UserDto addUserService(String name){
        return helloService.addUserService(name);
    }

    @RequestMapping(value = "/addUserService" ,method = RequestMethod.POST)
    public UserDto updateUserService(UserDto userDto){
        return helloService.updateUserService(userDto);
    }
}
