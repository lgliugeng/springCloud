package com.liugeng.hystrix;
import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import org.springframework.web.client.RestTemplate;

/**
 * 自定义Hystrix命令类
 *
 * @author   liugeng
 * @date     2020/3/13 16:22
*/
public class HelloHystrixCommand extends HystrixCommand<String> {

    private RestTemplate restTemplate;

    public HelloHystrixCommand(RestTemplate restTemplate){
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("HelloCommand"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HelloThread"))
                .andCommandPropertiesDefaults(
                        // 由于主次command已经使用线程池隔离，Facade Command使用信号量隔离即可
                        HystrixCommandProperties.Setter()
                                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)));
        this.restTemplate = restTemplate;
    }

    @Override
    protected String run() throws Exception {
        return restTemplate.getForEntity("http://HELLO-SERVICE/index",String.class).getBody();
    }

    @Override
    protected String getFallback() {
        return "Hystrix error";
    }

    @Override
    protected String getCacheKey() {
        // 设置缓存的key值
       return "Cache_key";
    }

    public static void flushCache(String key){
        // 清除key值的缓存
        HystrixRequestCache
                .getInstance(HystrixCommandKey.Factory.asKey("HelloCommand"),
                        HystrixConcurrencyStrategyDefault.getInstance())
                .clear(key);
    }

    class HelloPostHystrixCommand extends HystrixCommand<String> {

        private RestTemplate restTemplate;

        public HelloPostHystrixCommand(RestTemplate restTemplate){
            super(Setter
                    .withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloGroup"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("HelloCommand"))
                    .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HelloThread"))
                    .andCommandPropertiesDefaults(
                            // 由于主次command已经使用线程池隔离，Facade Command使用信号量隔离即可
                            HystrixCommandProperties.Setter()
                                    .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)));
            this.restTemplate = restTemplate;
        }

        @Override
        protected String run() throws Exception {
            // 清除原先缓存，将现在返回的结果作为新的缓存结果
            HelloHystrixCommand.flushCache(getCacheKey());
            return restTemplate.getForEntity("http://HELLO-SERVICE/index",String.class).getBody() + "2222";
        }

        @Override
        protected String getFallback() {
            return "Hystrix error";
        }
    }
}
