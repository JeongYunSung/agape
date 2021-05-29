package com.yunseong.core.market;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreateMarketRequest {

    private MarketVO market;
    private RestaurantVO restaurant;
    private List<FoodVO> foods;
}
