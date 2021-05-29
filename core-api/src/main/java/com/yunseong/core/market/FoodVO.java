package com.yunseong.core.market;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FoodVO {

    private String name;
    private String description;
    private int count;
    private int amount;
}
