package com.liugeng.service.impl;

import com.liugeng.refactoryDto.UserDto;
import com.liugeng.service.FeignHelloService;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * 实现feign服务功能方法业务逻辑
 * 
 * @author   liugeng
 * @date     2020/3/17 15:59
*/
@Service
public class FeignHelloServiceImpl{

    private static Long ID_VALUE = 1L;

    public String helloUserService(UserDto userDto) {
        return "hello:" + userDto.getName() + "你的ID：" + userDto.getId();
    }

    public UserDto addUserService(String name) {
        UserDto userDto = new UserDto();
        userDto.setId(ID_VALUE);
        userDto.setName(name);
        ID_VALUE++;
        return userDto;
    }

    public UserDto updateUserService(UserDto userDto) {
        userDto.setName(userDto.getName() + new Random().nextInt(10000));
        return userDto;
    }
}
