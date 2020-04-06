package com.liugeng.feign;

import feign.codec.DecodeException;
import feign.codec.EncodeException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractFallback {
    protected static final Logger logger = LoggerFactory.getLogger(AbstractFallback.class);
    protected final Throwable cause;

    public AbstractFallback(Throwable cause) {
        this.cause = cause instanceof DecodeException || cause instanceof EncodeException ? cause.getCause() : cause;
    }

    /**
     * 异常处理
     * <pre>
     *     每个熔断方法中都必须首先调用该方法
     * </pre>
     */
    protected void handleKnownException() {
        logger.warn("-Feign客户端调用异常: {}", Thread.currentThread().getStackTrace()[2]);
        logger.warn("|");
        Throwable ex = ExceptionUtils.getCause(this.cause);
        logger.warn("|--接口调用出错: ", this.cause);
        throw new RuntimeException(ex);
    }
}
