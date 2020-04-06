package com.liugeng.feign;

import feign.Feign;

public class FeignConfig {

    public static <T> T getFeignConfih(Class<T> target){
        return Feign.builder().target(null);
    }
}
