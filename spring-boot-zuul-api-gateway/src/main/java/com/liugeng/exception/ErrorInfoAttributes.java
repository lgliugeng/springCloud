package com.liugeng.exception;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

public class ErrorInfoAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        // 获取响应信息
        Map<String, Object> result = super.getErrorAttributes(webRequest, includeStackTrace);
        result.remove("error");
        return result;
    }
}
