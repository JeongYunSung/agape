package com.yunseong.core.order.service;

import com.yunseong.core.order.domain.Order;
import com.yunseong.core.payment.KakaoPayReadyRequest;
import com.yunseong.core.payment.KakaoPayReadyResponse;
import com.yunseong.core.payment.domain.Payment;
import com.yunseong.core.payment.service.KakaoPayService;

public class KakaoPayServiceStub extends KakaoPayService {

    public KakaoPayServiceStub() {
        super(null, null, null);
    }

    @Override
    public KakaoPayReadyResponse ready(Order order) {
        return new KakaoPayReadyResponse();
    }

    @Override
    public void approve(long id, String pg_token) {
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
