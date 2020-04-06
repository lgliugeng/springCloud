package com.liugeng.service;

import com.liugeng.dto.UserDto;

public interface HelloService {

    String helloUserService(UserDto userDto);

    UserDto addUserService(String name);

    UserDto updateUserService(UserDto userDto);
}
