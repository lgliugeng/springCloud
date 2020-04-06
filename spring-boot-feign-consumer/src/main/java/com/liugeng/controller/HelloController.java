package com.liugeng.controller;


import com.liugeng.dto.UserDto;
import com.liugeng.service.remote.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @RequestMapping(value = "/helloUserService" ,method = RequestMethod.POST)
    public String helloUserService(@RequestBody UserDto userDto) {
        return helloService.helloUserService(userDto);
    }

    @RequestMapping(value = "/addUserService" ,method = RequestMethod.GET)
    public UserDto addUserService(@RequestParam String name) {
        return helloService.addUserService(name);
    }

    @RequestMapping(value = "/addUserService" ,method = RequestMethod.POST)
    public UserDto updateUserService(@RequestBody UserDto userDto) {
        return helloService.updateUserService(userDto);
    }
}
