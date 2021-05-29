package com.yunseong.core.common.exception;

public class DifferentOwnerException extends RuntimeException {

    public DifferentOwnerException(Class<?> clazz, String email) {
        super(email + "은 해당 " + clazz.getSimpleName() + " 엔티티의 소유자가 아닙니다.");
    }
}
