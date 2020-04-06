package com.liugeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class SpringBootHystrixServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootHystrixServerApplication.class, args);
    }

}
