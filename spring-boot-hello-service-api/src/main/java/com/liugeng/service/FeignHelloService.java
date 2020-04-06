package com.liugeng.service;

import com.liugeng.refactoryDto.UserDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 提供feign服务调用的接口方法
 * 
 * @author   liugeng
 * @date     2020/3/17 15:59
*/
@RequestMapping("/refactor")
public interface FeignHelloService {

    @RequestMapping(value = "/helloUserService" ,method = RequestMethod.POST)
    String helloUserService(@RequestBody UserDto userDto);

    @RequestMapping(value = "/addUserService" ,method = RequestMethod.GET)
    UserDto addUserService(@RequestParam String name);

    @RequestMapping(value = "/updateUserService" ,method = RequestMethod.POST)
    UserDto updateUserService(@RequestBody UserDto userDto);
}
