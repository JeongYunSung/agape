package com.yunseong.core.order;

import com.yunseong.core.payment.PaymentMethod;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreateOrderRequest {

    private PaymentMethod paymentMethod;
    private List<FoodVO> foods;
}
