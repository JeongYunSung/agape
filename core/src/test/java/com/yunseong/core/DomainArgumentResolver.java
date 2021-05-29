package com.yunseong.core;

import com.yunseong.core.market.service.MarketArgumentResolver;
import com.yunseong.core.order.service.OrderArgumentResolver;

import java.util.Optional;
import java.util.Random;

public interface DomainArgumentResolver {

    Optional<Object> tryResolve(Class<?> parameterType);

    Random RANDOM = new Random();

    DomainArgumentResolver INSTANCE = new CompositeArgumentResolver(new MarketArgumentResolver(), new OrderArgumentResolver());
}
