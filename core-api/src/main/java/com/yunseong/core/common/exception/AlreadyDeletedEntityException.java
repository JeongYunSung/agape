package com.yunseong.core.common.exception;

public class AlreadyDeletedEntityException extends RuntimeException {

    public AlreadyDeletedEntityException(Class<?> clazz) {
        super(clazz.getSimpleName() + "엔티티는 이미 삭제된 상태입니다.");
    }
}
