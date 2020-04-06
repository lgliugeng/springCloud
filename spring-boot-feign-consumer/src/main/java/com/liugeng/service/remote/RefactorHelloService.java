package com.liugeng.service.remote;

import com.liugeng.service.FeignHelloService;
import org.springframework.cloud.openfeign.FeignClient;

/**只能存在一个服务实例的feign客户端*/
@FeignClient("HELLO-SERVICE1")
public interface RefactorHelloService extends FeignHelloService {
}
