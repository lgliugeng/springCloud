package com.liugeng.controller;

import com.liugeng.service.HelloService;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;

import java.util.concurrent.Future;

@RestController
public class RibbonHelloController {

    @Autowired
    private HelloService helloService;

    @RequestMapping(value = "/ribbonHelloHystrixCollapser",method = RequestMethod.GET)
    public String ribbonHelloHystrixCollapser() throws Exception{
        HystrixRequestContext.initializeContext();
        String result = helloService.getHelloHystrixCollapser(1L);
        String result1 = helloService.getHelloHystrixCollapser(2L);
        String result2 = helloService.getHelloHystrixCollapser(3L);
        System.out.println(result);
        System.out.println(result1);
        System.out.println(result2);
        return result;
    }

    @RequestMapping(value = "/ribbonHello",method = RequestMethod.GET)
    public String HelloConsumer(){
        // 初始化Hystrix请求上下文,使用缓存必须要先初始化上下文,一般放在Filter中
        HystrixRequestContext.initializeContext();
        // 在同一个上下文中，只有首个会请求服务，其余的请求只会从缓存中获取
        System.out.println(helloService.getHelloService());
        System.out.println(helloService.getHelloService());
        System.out.println(helloService.getHelloService());
        return helloService.getHelloService();
    }

    @RequestMapping(value = "/ribbonHelloCacheUpdate",method = RequestMethod.GET)
    public String HelloConsumerCacheUpdate(){
        // 初始化Hystrix请求上下文
        HystrixRequestContext.initializeContext();
        return helloService.getHelloServiceCacheUpdate();
    }

    @RequestMapping(value = "/ribbonHelloAsync",method = RequestMethod.GET)
    public Future<String> HelloConsumerAsync(){
        return helloService.getHelloServiceAsync();
    }

    @RequestMapping(value = "/ribbonHelloByClass",method = RequestMethod.GET)
    public String HelloConsumerByClass(){
        return helloService.getHelloServiceByCommandClass();
    }

    @RequestMapping(value = "/ribbonHelloByClassAsync",method = RequestMethod.GET)
    public Future<String> HelloConsumerByClassAsync(){
        return helloService.getHelloServiceByCommandClassAsync();
    }

    @RequestMapping(value = "/ribbonHelloByClassObserve",method = RequestMethod.GET)
    public Observable<String> HelloConsumerByClassObserve(){
        return helloService.getHelloServiceByCommandClassObserve();
    }

    @RequestMapping(value = "/ribbonHelloByClassAsyncObserve",method = RequestMethod.GET)
    public Observable<String> HelloConsumerByClassAsyncObserve(){
        return helloService.getHelloServiceByCommandClassAsyncObserve();
    }

}
