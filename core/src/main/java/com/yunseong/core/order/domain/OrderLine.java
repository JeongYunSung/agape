package com.yunseong.core.order.domain;

import com.yunseong.core.market.domain.Food;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@RequiredArgsConstructor
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "order_name")
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @JoinColumn(name = "food_name")
    @ManyToOne(fetch = FetchType.LAZY)
    private Food food;

    @NonNull
    private int count;

    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime updatedTime;

    public void setOrder(Order order) {
        if(order != null) {
            order.getOrders().remove(this);
        }
        this.order = order;
        order.getOrders().add(this);
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getPrice() {
        return food.getAmount() * this.count;
    }
}
