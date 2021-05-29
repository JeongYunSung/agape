package com.yunseong.core.market.domain;

import com.yunseong.core.common.exception.FoodSoldOutException;
import com.yunseong.core.common.exception.UnsupportedStateTransitionException;
import com.yunseong.core.market.FoodVO;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@RequiredArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private int count;

    @NonNull
    private int amount;

    @JoinColumn(name = "market_name")
    @ManyToOne(fetch = FetchType.LAZY)
    private Market market;

    @Enumerated(value = EnumType.STRING)
    private FoodState foodState = FoodState.RELEASED;

    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime updatedTime;

    void setMarket(Market market) {
        if(this.market != null) {
            this.market.getFoods().remove(this);
        }
        this.market = market;
        this.market.getFoods().add(this);
    }

    public Food update(FoodVO foodVO) {
        if(this.foodState != FoodState.RELEASED) {
            throw new UnsupportedStateTransitionException(FoodState.RELEASED);
        }
        this.name = foodVO.getName();
        this.description = foodVO.getDescription();
        this.count = foodVO.getCount();
        this.amount = foodVO.getAmount();
        return this;
    }

    public void delete() {
        if(this.foodState != FoodState.RELEASED) {
            throw new UnsupportedStateTransitionException(FoodState.RELEASED);
        }
        this.foodState = FoodState.DELETE;
    }

    public Food buy(int count) {
        if(this.count < 0 ||this.count-count < 0) {
            throw new FoodSoldOutException(this.id, this.name);
        }
        this.count -= count;
        return this;
    }
}
