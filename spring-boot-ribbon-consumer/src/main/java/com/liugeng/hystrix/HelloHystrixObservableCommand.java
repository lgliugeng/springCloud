package com.liugeng.hystrix;
import com.netflix.hystrix.*;
import org.springframework.web.client.RestTemplate;
import rx.Emitter;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 自定义Hystrix命令类(返回多次发射信息)
 *
 * @author   liugeng
 * @date     2020/3/13 16:22
*/
public class HelloHystrixObservableCommand extends HystrixObservableCommand<String> {

    private RestTemplate restTemplate;

    public HelloHystrixObservableCommand(RestTemplate restTemplate){
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("HelloCommand"))
                .andCommandPropertiesDefaults(
                        // 由于主次command已经使用线程池隔离，Facade Command使用信号量隔离即可
                        HystrixCommandProperties.Setter()
                                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)));
        this.restTemplate = restTemplate;
    }

    @Override
    protected Observable<String> construct() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        String result = restTemplate.getForEntity("http://HELLO-SERVICE/index",String.class).getBody();
                        subscriber.onNext(result);
                        subscriber.onCompleted();
                    }
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    protected Observable<String> resumeWithFallback() {
        return super.resumeWithFallback();
    }
}
