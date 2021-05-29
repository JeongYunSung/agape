package com.yunseong.core.common.exception;

public class FoodSoldOutException extends RuntimeException {

    public FoodSoldOutException(long id, String food) {
        super("음식 : " + food + "(" + id + ")" + "의 재고가 전부 소진되었습니다.");
    }
}
