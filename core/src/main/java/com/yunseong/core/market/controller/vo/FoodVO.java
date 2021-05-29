package com.yunseong.core.market.controller.vo;

import com.yunseong.core.market.domain.Food;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FoodVO {

    private String name;
    private String description;
    private int count;
    private int amount;

    public FoodVO(Food food) {
        this.name = food.getName();
        this.description = food.getDescription();
        this.count = food.getCount();
        this.amount = food.getAmount();
    }

    public Food toFood() {
        return new Food(this.name, this.description, this.count, this.amount);
    }
}
