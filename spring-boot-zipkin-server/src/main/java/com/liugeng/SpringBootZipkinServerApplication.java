package com.liugeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin2.server.internal.EnableZipkinServer;

@EnableZipkinServer
@SpringBootApplication
@EnableDiscoveryClient
public class SpringBootZipkinServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootZipkinServerApplication.class, args);
    }

}
