package com.yunseong.core.market;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FindMarketDetailResponse {

    private MarketVO market;
    private RestaurantVO restaurant;
    private List<FoodVO> foods;
}
