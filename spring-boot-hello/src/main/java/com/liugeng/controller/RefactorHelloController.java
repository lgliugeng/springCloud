package com.liugeng.controller;

import com.liugeng.refactoryDto.UserDto;
import com.liugeng.service.FeignHelloService;
import com.liugeng.service.impl.FeignHelloServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 继承api公共服务的接口即对外提供服务，也对内提供feign调用
 * 
 * @author   liugeng
 * @date     2020/3/17 15:58
*/
@RestController
@RequestMapping("/refactor")
public class RefactorHelloController implements FeignHelloService {

    @Autowired
    private FeignHelloServiceImpl feignHelloService;

    @Override
    @RequestMapping(value = "/helloUserService" ,method = RequestMethod.POST)
    public String helloUserService(@RequestBody UserDto userDto) {
        return feignHelloService.helloUserService(userDto);
    }

    @Override
    @RequestMapping(value = "/addUserService" ,method = RequestMethod.GET)
    public UserDto addUserService(@RequestParam String name) {
        return feignHelloService.addUserService(name);
    }

    @Override
    @RequestMapping(value = "/updateUserService" ,method = RequestMethod.POST)
    public UserDto updateUserService(@RequestBody UserDto userDto) {
        return feignHelloService.updateUserService(userDto);
    }
}
