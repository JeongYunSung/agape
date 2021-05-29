package com.yunseong.core.payment.service;

import com.yunseong.core.payment.domain.PaymentMethod;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentServiceFactory {

    private final KakaoPayService kakaoPayService;

    public PaymentService getService(PaymentMethod method) {
        switch (method) {
            case KAKAO_PAY:
                return kakaoPayService;
            default:
                return null;
        }
    }
}
