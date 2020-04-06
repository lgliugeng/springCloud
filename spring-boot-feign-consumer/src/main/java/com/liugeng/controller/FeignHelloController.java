package com.liugeng.controller;

import com.liugeng.refactoryDto.UserDto;
import com.liugeng.service.remote.RefactorHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feign")
public class FeignHelloController {

    @Autowired
    private RefactorHelloService refactorHelloService;

    @RequestMapping(value = "/helloUserService" ,method = RequestMethod.POST)
    public String helloUserService(@RequestBody UserDto userDto) {
        return refactorHelloService.helloUserService(userDto);
    }

    @RequestMapping(value = "/addUserService" ,method = RequestMethod.GET)
    public UserDto addUserService(@RequestParam String name) {
        return refactorHelloService.addUserService(name);
    }

    @RequestMapping(value = "/addUserService" ,method = RequestMethod.POST)
    public UserDto updateUserService(@RequestBody UserDto userDto) {
        return refactorHelloService.updateUserService(userDto);
    }
}
