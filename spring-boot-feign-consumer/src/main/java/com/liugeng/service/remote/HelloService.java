package com.liugeng.service.remote;

import com.liugeng.dto.UserDto;
import com.liugeng.feign.AbstractFallback;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**只能存在一个服务实例的feign客户端*/
@FeignClient(value = "HELLO-SERVICE",fallbackFactory = HelloServiceFallbackFactory.class)
@Component
public interface HelloService{

    @RequestMapping(value = "/helloUserService" ,method = RequestMethod.POST)
    String helloUserService(UserDto userDto);

    @RequestMapping(value = "/addUserService" ,method = RequestMethod.GET)
    UserDto addUserService(String name);

    @RequestMapping(value = "/addUserService" ,method = RequestMethod.POST)
    UserDto updateUserService(UserDto userDto);
}

@Component
class HelloServiceFallbackFactory implements FallbackFactory<HelloService>{
    @Override
    public HelloService create(Throwable cause) {
        return new HelloServiceFallback(cause);
    }

    class HelloServiceFallback extends AbstractFallback implements HelloService{

        public HelloServiceFallback(Throwable cause){
            super(cause);
        }

        @Override
        public String helloUserService(UserDto userDto) {
            handleKnownException();
            throw new RuntimeException("helloUserService出错");
        }

        @Override
        public UserDto addUserService(String name) {
            handleKnownException();
            throw new RuntimeException("addUserService出错");
        }

        @Override
        public UserDto updateUserService(UserDto userDto) {
            handleKnownException();
            throw new RuntimeException("updateUserService出错");
        }
    }
}
