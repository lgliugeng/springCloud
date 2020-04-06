package com.liugeng.service.impl;

import com.liugeng.dto.UserDto;
import com.liugeng.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class HelloServiceImpl implements HelloService {

    private static Long ID_VALUE = 1L;

    @Autowired
    private DiscoveryClient client;

    @Override
    public String helloUserService(UserDto userDto) {
        List<String> stringList = client.getServices();
        int sleepTime = new Random().nextInt(3000);
        System.out.println("模拟逻辑休眠：" + sleepTime + "毫秒");
        try {
            // 断路器超时时间为2000毫秒
            Thread.sleep(sleepTime);
        }catch (Exception e){}
        stringList.forEach(s -> {
            List<ServiceInstance> instances = client.getInstances(s);
            instances.forEach(serviceInstance -> {
                System.out.println("访问/index的应用信息：Host:" + serviceInstance.getHost() + "ServiceId:" + serviceInstance.getServiceId());
            });
        });
        return "hello:" + userDto.getName() + "你的ID：" + userDto.getId();
    }

    @Override
    public UserDto addUserService(String name) {
        UserDto userDto = new UserDto();
        userDto.setId(ID_VALUE);
        userDto.setName(name);
        ID_VALUE++;
        return userDto;
    }

    @Override
    public UserDto updateUserService(UserDto userDto) {
        userDto.setName(userDto.getName() + new Random().nextInt(10000));
        return userDto;
    }
}
