package com.yunseong.core.market.controller.vo;

import com.yunseong.core.market.domain.Restaurant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RestaurantVO {

    private String name;
    private String description;

    public RestaurantVO(Restaurant restaurant) {
        this.name = restaurant.getName();
        this.description = restaurant.getDescription();
    }

    public Restaurant toRestaurant() {
        return new Restaurant(this.name, this.description);
    }
}
