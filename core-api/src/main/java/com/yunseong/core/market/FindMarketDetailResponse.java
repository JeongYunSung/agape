package com.yunseong.core.market;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class FindMarketDetailResponse {

    private MarketVO market;
    private RestaurantVO restaurant;
    private List<FoodVO> foods;
}
