package com.yunseong.core.order.domain;

import com.yunseong.core.common.exception.UnsupportedStateTransitionException;
import com.yunseong.core.member.domain.Member;
import com.yunseong.core.payment.domain.Payment;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@EnableJpaAuditing
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @JoinColumn(name = "orderer_name")
    @OneToOne(fetch = FetchType.LAZY)
    private Member orderer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLine> orders = new ArrayList<>();

    @JoinColumn(name = "payment_name")
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order")
    private Payment payment;

    @Enumerated(value = EnumType.STRING)
    private OrderState orderState = OrderState.ORDER_PROCESSING;

    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime updatedTime;

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void order() {
        if(this.orderState != OrderState.ORDER_PROCESSING) {
            throw new UnsupportedStateTransitionException(this.orderState);
        }
        this.orderState = OrderState.ORDERED;
    }

    public void cancel() {
        if(this.orderState != OrderState.ORDER_PROCESSING) {
            throw new UnsupportedStateTransitionException(this.orderState);
        }
        this.orderState = OrderState.CANCELED;
    }

    public int getTotalCount() {
        return this.orders.stream().map(OrderLine::getCount).reduce((a, b) -> a+b).get();
    }

    public int getTotalAmount() {
        return this.orders.stream().map(OrderLine::getPrice).reduce((a, b) -> a+b).get();
    }
}
