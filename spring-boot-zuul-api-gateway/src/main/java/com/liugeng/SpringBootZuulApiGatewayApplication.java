package com.liugeng;

import com.liugeng.exception.ErrorInfoAttributes;
import com.liugeng.filter.AccessFilter;
import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;

import java.io.File;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class SpringBootZuulApiGatewayApplication {

    /**请求过滤*/
    @Bean
    AccessFilter accessFilter () {
        return new AccessFilter();
    }

    /**对响应信息重定义*/
    @Bean
    ErrorInfoAttributes errorInfoAttributes(){
        return new ErrorInfoAttributes();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootZuulApiGatewayApplication.class, args);
    }

    /**通过Groovy动态加载过滤器实例*/
    /*@Bean
    public FilterLoader filterLoader() {
        FilterLoader filterLoader = FilterLoader.getInstance();
        filterLoader.setCompiler(new GroovyCompiler());
        // 读取配置，获取脚本根目录
        String scriptRoot = System.getProperty("zuul.filter.root", "groovy/filters");
        // 获取刷新间隔
        String refreshInterval = System.getProperty("zuul.filter.refreshInterval", "5");
        if (scriptRoot.length() > 0) scriptRoot = scriptRoot + File.separator;
        try {
            FilterFileManager.setFilenameFilter(new GroovyFileFilter());
            FilterFileManager.init(Integer.parseInt(refreshInterval), scriptRoot + "pre",
                    scriptRoot + "route", scriptRoot + "post");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return filterLoader;
    }*/

}
