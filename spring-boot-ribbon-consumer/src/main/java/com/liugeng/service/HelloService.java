package com.liugeng.service;

import com.liugeng.hystrix.HelloHystrixCommand;
import com.liugeng.hystrix.HelloHystrixObservableCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class HelloService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 合并请求
     *
     * @param 
    
     * @return    java.lang.String
     * @author    liugeng     
     * @date      2020/3/16 16:24
    */
    @HystrixCollapser(batchMethod = "getHelloServiceTest",collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds",value = "1000")
    })
    public String getHelloHystrixCollapser(Long id){
        return null;
    }

    /**
     * 测试合并请求
     *
     * @param

     * @return    java.lang.String
     * @author    liugeng
     * @date      2020/3/16 16:25
    */
    @HystrixCommand
    public List<String> getHelloServiceTest(List<Long> ids){
        long start = System.currentTimeMillis();
        String result = restTemplate.getForObject("http://HELLO-SERVICE/indexs?id={1}" ,String.class,StringUtils.join(ids,","));
        long end = System.currentTimeMillis();
        logger.info("方法执行时间：" + (end-start) + "毫秒");
        return Arrays.asList(result.split(","));
    }

    /**
     * ribbon访问hello服务提供(使用断路器)(同步使用)
     *
     * @param  null
    
     * @return    java.lang.String
     * @author    liugeng     
     * @date      2020/3/9 10:31
    */
    /**commandKey为全局唯一标识的服务名称*/
    /**commandProperties配置相关的隔离策略、线程数、超时等*/
    @HystrixCommand(fallbackMethod = "helloFallbackHystrix",commandKey = "helloKey",
    commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")
    })
    @CacheResult(cacheKeyMethod = "cacheKey")
    public String getHelloService(){
        long start = System.currentTimeMillis();
        String result = restTemplate.getForEntity("http://HELLO-SERVICE/index",String.class).getBody();
        long end = System.currentTimeMillis();
        logger.info("方法执行时间：" + (end-start) + "毫秒");
        return result;
    }

    /**
     * ribbon访问hello服务提供(使用断路器)(同步使用)
     *
     * @param  null

     * @return    java.lang.String
     * @author    liugeng
     * @date      2020/3/9 10:31
     */
    /**commandKey为全局唯一标识的服务名称*/
    /**commandProperties配置相关的隔离策略、线程数、超时等*/
    @HystrixCommand(fallbackMethod = "helloFallbackHystrix",commandKey = "helloKey",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")
            })
    @CacheRemove(commandKey = "helloKey")
    public String getHelloServiceCacheUpdate(){
        long start = System.currentTimeMillis();
        String result = restTemplate.getForEntity("http://HELLO-SERVICE/index",String.class).getBody();
        long end = System.currentTimeMillis();
        logger.info("方法执行时间：" + (end-start) + "毫秒");
        return result + "2";
    }

    /**
     * ribbon访问hello服务提供(使用断路器)(同步使用)（异步使用）
     *
     * @param     null
    
     * @return    java.util.concurrent.Future<java.lang.String>
     * @author    liugeng     
     * @date      2020/3/13 16:28
    */
    @HystrixCommand(fallbackMethod = "helloFallbackHystrix",commandKey = "helloKey",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")
            })
    public Future<String> getHelloServiceAsync(){
        long start = System.currentTimeMillis();
        Future<String> result = new AsyncResult<String>(){
            @Override
            public String invoke() {
                return restTemplate.getForEntity("http://HELLO-SERVICE/index",String.class).getBody();
            }
        };
        long end = System.currentTimeMillis();
        logger.info("方法执行时间：" + (end-start) + "毫秒");
        return result;
    }

    /**
     * ribbon访问hello服务提供(使用断路器自定义类)(同步使用)
     *
     * @param      null
    
     * @return    java.lang.String
     * @author    liugeng     
     * @date      2020/3/13 16:34
    */
    public String getHelloServiceByCommandClass(){
        long start = System.currentTimeMillis();
        String result = new HelloHystrixCommand(restTemplate).execute();
        long end = System.currentTimeMillis();
        logger.info("方法执行时间：" + (end-start) + "毫秒");
        return result;
    }

    /**
     * ribbon访问hello服务提供(使用断路器自定义类)(异步使用)
     *
     * @param      null

     * @return    java.lang.String
     * @author    liugeng
     * @date      2020/3/13 16:34
     */
    public Future<String> getHelloServiceByCommandClassAsync(){
        long start = System.currentTimeMillis();
        Future<String> result = new HelloHystrixCommand(restTemplate).queue();
        long end = System.currentTimeMillis();
        logger.info("方法执行时间：" + (end-start) + "毫秒");
        return result;
    }

    /**
     * ribbon访问hello服务提供(使用断路器自定义类返回Observe对象)(默认创建一个订阅者，因此不需要订阅者,只返回一次，因为继承的HystrixCommand)
     *
     * @param      null

     * @return    java.lang.String
     * @author    liugeng
     * @date      2020/3/13 16:34
     */
    public Observable<String> getHelloServiceByCommandClassObserve(){
        long start = System.currentTimeMillis();
        Observable<String> result = new HelloHystrixCommand(restTemplate).observe();
        long end = System.currentTimeMillis();
        logger.info("方法执行时间：" + (end-start) + "毫秒");
        return result;
    }

    /**
     * ribbon访问hello服务提供(使用断路器自定义类返回Observe对象)(必须存在订阅者,只返回一次，因为继承的HystrixCommand)
     *
     * @param      null

     * @return    java.lang.String
     * @author    liugeng
     * @date      2020/3/13 16:34
     */
    public Observable<String> getHelloServiceByCommandClassAsyncObserve(){
        long start = System.currentTimeMillis();
        Observable<String> result = new HelloHystrixCommand(restTemplate).toObservable();
        long end = System.currentTimeMillis();
        logger.info("方法执行时间：" + (end-start) + "毫秒");
        return result;
    }

    /**
     * ribbon访问hello服务提供(使用断路器自定义类返回Observe对象)(默认创建一个订阅者，因此不需要订阅者,返回多次，因为继承的HystrixObserveCommand)
     *
     * @param      null

     * @return    java.lang.String
     * @author    liugeng
     * @date      2020/3/13 16:34
     */
    public Observable<String> getHelloServiceByObserveCommandClassObserve(){
        long start = System.currentTimeMillis();
        Observable<String> result = new HelloHystrixObservableCommand(restTemplate).observe();
        result.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                long end = System.currentTimeMillis();
                logger.info("方法执行时间：" + (end-start) + "毫秒");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                logger.info("信息：" + s);
            }
        });
        return result;
    }

    /**
     * ribbon访问hello服务提供(使用断路器自定义类返回Observe对象)(必须存在订阅者,返回多次，因为继承的HystrixObserveCommand)
     *
     * @param      null

     * @return    java.lang.String
     * @author    liugeng
     * @date      2020/3/13 16:34
     */
    public Observable<String> getHelloServiceByObserveCommandClassAsyncObserve(){
        long start = System.currentTimeMillis();
        Observable<String> result = new HelloHystrixCommand(restTemplate).toObservable();
        long end = System.currentTimeMillis();
        logger.info("方法执行时间：" + (end-start) + "毫秒");
        return result;
    }

    /**
     * 断路器保护访问hello服务异常返回方法
     *
     * @param  null
    
     * @return    java.lang.String
     * @author    liugeng     
     * @date      2020/3/9 10:32
    */
    private String helloFallbackHystrix(){
        return "Access Error";
    }

    /**
     * 指定缓存key
     *
     * @param null
    
     * @return    java.lang.String
     * @author    liugeng     
     * @date      2020/3/13 18:03
    */
    private String cacheKey() {
        return "hello_key";
    }
}
