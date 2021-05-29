package com.yunseong.core.market.controller.vo;

import com.yunseong.core.market.domain.Market;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class FindMarketDetailResponse {

    private MarketVO market;
    private RestaurantVO restaurant;
    private List<FoodVO> foods;

    public FindMarketDetailResponse(Market market) {
        this.market = new MarketVO(market);
        this.restaurant = new RestaurantVO(market.getRestaurant());
        this.foods = market.getFoods().stream().map(FoodVO::new).collect(toList());
    }
}
