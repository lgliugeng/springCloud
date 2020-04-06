package com.liugeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableTurbine
@EnableDiscoveryClient
public class SpringBootHystrixTurbineServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootHystrixTurbineServerApplication.class, args);
    }

}
