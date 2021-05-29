package com.yunseong.core.common.exception;

public class UnsupportedStateTransitionException extends RuntimeException {

    public UnsupportedStateTransitionException(Enum state) {
        super("현재 (" + state + ")상태로 변경할 수 없습니다");
    }
}
