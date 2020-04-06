package com.liugeng.exception;

import com.netflix.zuul.exception.ZuulException;

/**自定义异常类*/
public class AuthException extends ZuulException {

    public AuthException(Throwable throwable, String sMessage, int nStatusCode, String errorCause) {
        super(throwable, sMessage, nStatusCode, errorCause);
    }

    public AuthException(String sMessage, int nStatusCode, String errorCause) {
        super(sMessage, nStatusCode, errorCause);
    }

    public AuthException(Throwable throwable, int nStatusCode, String errorCause) {
        super(throwable, nStatusCode, errorCause);
    }
}
