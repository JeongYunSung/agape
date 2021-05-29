package com.yunseong.core.payment.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoPayPayment extends Payment {

    private String tid;

    public KakaoPayPayment(String tid, PaymentMethod method) {
        this.tid = tid;
        this.paymentMethod = method;
    }
}
