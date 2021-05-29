package com.yunseong.core.common.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<?> clazz, String id) {
        super("Class : " + clazz.getSimpleName() + ", ID : " + id + "에 해당하는 엔티티는 존재하지 않습니다.");
    }
}
