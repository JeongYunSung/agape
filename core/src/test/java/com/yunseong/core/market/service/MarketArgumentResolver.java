package com.yunseong.core.market.service;

import com.yunseong.core.DomainArgumentResolver;
import com.yunseong.core.market.controller.vo.CreateMarketRequest;
import com.yunseong.core.market.controller.vo.FoodVO;
import com.yunseong.core.market.controller.vo.MarketVO;
import com.yunseong.core.market.controller.vo.RestaurantVO;
import com.yunseong.core.market.domain.Food;
import com.yunseong.core.market.domain.Market;
import com.yunseong.core.market.domain.Restaurant;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

public class MarketArgumentResolver implements DomainArgumentResolver {

    @Override
    public Optional<Object> tryResolve(Class<?> parameterType) {
        if(parameterType.equals(CreateMarketRequest.class)) {
            return Optional.of(generate());
        }
        if(parameterType.equals(CreateMarketRequest[].class)) {
            return Optional.of(new CreateMarketRequest[] {generate(), generate(), generate(), generate(), generate()});
        }
        return Optional.empty();
    }

    private CreateMarketRequest generate() {
        MarketVO marketVO =
                new MarketVO(new Market(
                        "마켓 제목 : " + UUID.randomUUID().toString(),
                        "마켓 설명 : " + UUID.randomUUID().toString(),
                        "위치 : " + UUID.randomUUID().toString(),
                        100000 + RANDOM.nextInt(100000),
                        200000 + RANDOM.nextInt(200000),
                        LocalDate.of(2021, 6, RANDOM.nextInt(29)+1),
                        LocalDate.of(2021, 7, RANDOM.nextInt(29)+1)));
        RestaurantVO restaurantVO = new RestaurantVO(new Restaurant(
                "식당 : " + UUID.randomUUID().toString(),
                "설명 : " + UUID.randomUUID().toString()));
        List<FoodVO> foods = new ArrayList<>();
        IntStream.range(1, 5).forEach(i -> foods.add(new FoodVO(new Food(
        "음식 : " + UUID.randomUUID().toString(),
        "설명 : " + UUID.randomUUID().toString(),
        RANDOM.nextInt(100000)+10000,
        RANDOM.nextInt(90)+10))));
        return new CreateMarketRequest(marketVO, restaurantVO, foods);
    }
}
