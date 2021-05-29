package com.yunseong.core.order.service;

import com.yunseong.core.DomainArgumentResolver;
import com.yunseong.core.order.CreateOrderRequest;
import com.yunseong.core.order.FoodVO;
import com.yunseong.core.payment.PaymentMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class OrderArgumentResolver implements DomainArgumentResolver {

    @Override
    public Optional<Object> tryResolve(Class<?> parameterType) {
        if(parameterType.equals(CreateOrderRequest.class)) {
            return Optional.of(generate());
        }
        return Optional.empty();
    }

    private CreateOrderRequest generate() {
        List<FoodVO> foods = new ArrayList<>();
        IntStream.range(1, 5).forEach(i -> foods.add(new FoodVO(1, i, 3)));
        return new CreateOrderRequest(PaymentMethod.KAKAO_PAY, foods);
    }
}
