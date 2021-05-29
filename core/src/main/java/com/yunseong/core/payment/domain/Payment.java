package com.yunseong.core.payment.domain;

import com.yunseong.core.common.exception.UnsupportedStateTransitionException;
import com.yunseong.core.order.domain.Order;
import com.yunseong.core.payment.PaymentMethod;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String tid;

    @OneToOne(fetch = FetchType.LAZY)
    private Order order;

    @NonNull
    @Enumerated(value = EnumType.STRING)
    protected PaymentMethod paymentMethod;

    @Enumerated(value = EnumType.STRING)
    private PaymentState paymentState = PaymentState.READY;

    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime updatedTime;

    public void setOrder(Order order) {
        if(this.order != null) {
            this.order.setPayment(null);
        }
        this.order = order;
        order.setPayment(this);
    }

    public void cancel() {
        if(this.paymentState != PaymentState.READY) {
            throw new UnsupportedStateTransitionException(this.paymentState);
        }
        this.paymentState = PaymentState.CANCELED;
    }

    public void refund() {
        if(this.paymentState != PaymentState.APPROVED) {
            throw new UnsupportedStateTransitionException(this.paymentState);
        }
        this.paymentState = PaymentState.REFUNDED;
    }
}
