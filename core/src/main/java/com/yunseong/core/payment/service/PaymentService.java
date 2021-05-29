package com.yunseong.core.payment.service;

import com.yunseong.core.order.domain.Order;
import com.yunseong.core.payment.domain.Payment;

public interface PaymentService {

    <T> T ready(Order order);

    void refund(Payment payment);
}
