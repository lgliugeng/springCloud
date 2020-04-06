package com.liugeng.filter;

import com.liugeng.exception.AuthException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     PRE Filter：在请求路由到目标之前执行。一般用于请求认证、负载均衡和日志记录。
     ROUTING Filter：处理目标请求。这里使用Apache HttpClient或Netflix Ribbon构造对目标的HTTP请求。
     POST Filter：在目标请求返回后执行。一般会在此步骤添加响应头、收集统计和性能数据等。
     ERROR Filter：整个流程某块出错时执行。
    */
    @Override
    public String filterType() {
        return "pre";
    }

    /**相同Type的Filter的执行顺序*/
    @Override
    public int filterOrder() {
        return 0;
    }

    /**判断过滤器是否被执行*/
    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String token = request.getHeader("Authorization");
        System.out.println("token：" + token);
        if (StringUtils.isEmpty(token)) {
            return true;
        }
        return false;
    }

    /**过滤器的具体逻辑*/
    @Override
    public Object run() throws ZuulException {
        try {
            throw new RuntimeException("token验证失败");
        }catch (Exception e){
            throw new AuthException(e,500,e.getMessage());
        }
        /*RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletResponse response = currentContext.getResponse();
        response.setCharacterEncoding("utf-8");
        currentContext.setResponseStatusCode(401);
        currentContext.setResponseBody("token校验失败");
        currentContext.setResponse(response);
        currentContext.setSendZuulResponse(false);
        return currentContext;*/
    }
}
