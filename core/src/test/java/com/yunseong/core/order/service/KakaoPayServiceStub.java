package com.yunseong.core.order.service;

import com.yunseong.core.order.domain.Order;
import com.yunseong.core.payment.controller.vo.KakaoPayReadyVO;
import com.yunseong.core.payment.domain.Payment;
import com.yunseong.core.payment.service.KakaoPayService;

public class KakaoPayServiceStub extends KakaoPayService {

    public KakaoPayServiceStub() {
        super(null, null, null);
    }

    @Override
    public KakaoPayReadyVO ready(Order order) {
        return new KakaoPayReadyVO();
    }

    @Override
    public void approve(String email, long id, String pg_token) {
    }

    @Override
    public void cancel(long id) {
    }

    @Override
    public void refund(long id) {
    }

    @Override
    public void refund(Payment payment) {
    }
}
