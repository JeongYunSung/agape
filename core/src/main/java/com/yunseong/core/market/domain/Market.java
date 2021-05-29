package com.yunseong.core.market.domain;

import com.yunseong.core.common.exception.EntityNotFoundException;
import com.yunseong.core.common.exception.UnsupportedStateTransitionException;
import com.yunseong.core.market.FoodVO;
import com.yunseong.core.market.MarketVO;
import com.yunseong.core.market.RestaurantVO;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@RequiredArgsConstructor
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String description;

    @NonNull
    private String location;

    @NonNull
    private int currentAmount;

    @NonNull
    private int targetAmount;

    @NonNull
    private LocalDate startTime;

    @NonNull
    private LocalDate endTime;

    @JoinColumn(name = "restaurant_name")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Restaurant restaurant;

    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL)
    private List<Food> foods = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private MarketState marketState = MarketState.WAITING;

    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime updatedTime;

    public void updateMarket(MarketVO marketVO) {
        if(this.marketState != MarketState.WAITING) {
            throw new UnsupportedStateTransitionException(this.marketState);
        }
        this.title = marketVO.getTitle();
        this.description = marketVO.getDescription();
        this.currentAmount = marketVO.getCurrentAmount();
        this.targetAmount = marketVO.getTargetAmount();
        this.startTime = marketVO.getStartTime();
        this.endTime = marketVO.getEndTime();
    }

    public void deleteMarket() {
        if(this.marketState == MarketState.DELETE) {
            throw new UnsupportedStateTransitionException(this.marketState);
        }
        this.marketState = MarketState.DELETE;
    }

    public void registerRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void addFood(Food food) {
        food.setMarket(this);
    }

    public void updateFood(long id, FoodVO food) {
        this.getFood(id).update(food);
    }

    public void deleteFood(long id) {
        this.getFood(id).delete();
    }

    public void updateRestaurant(RestaurantVO restaurantVO) {
        this.getRestaurant().update(restaurantVO);
    }

    public Food buyFood(long id, int count) {
        return this.getFood(id).buy(count);
    }

    private Food getFood(long id) {
        return this.getFoods().stream()
                .filter(food -> food.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(Food.class, String.valueOf(id)));
    }
}
