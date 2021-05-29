package com.yunseong.core.common.exception;

public class RequestNotEmptyException extends RuntimeException {

    public RequestNotEmptyException(String message) {
        super(message);
    }
}
