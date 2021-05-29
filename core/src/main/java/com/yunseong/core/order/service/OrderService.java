package com.yunseong.core.order.service;

import com.yunseong.core.common.exception.DifferentOwnerException;
import com.yunseong.core.common.exception.EntityNotFoundException;
import com.yunseong.core.market.service.MarketService;
import com.yunseong.core.member.service.MemberDetailsService;
import com.yunseong.core.order.CreateOrderRequest;
import com.yunseong.core.order.CreateOrderResponse;
import com.yunseong.core.order.FoodVO;
import com.yunseong.core.order.domain.Order;
import com.yunseong.core.order.domain.OrderLine;
import com.yunseong.core.order.domain.OrderRepository;
import com.yunseong.core.payment.service.PaymentServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final MemberDetailsService memberDetailsService;
    private final MarketService marketService;
    private final PaymentServiceFactory paymentServiceFactory;

    public CreateOrderResponse requestOrder(String email, CreateOrderRequest request) {
        Order order = new Order(this.memberDetailsService.findMember(email));

        for(FoodVO food : request.getFoods()) {
            OrderLine orderLine = new OrderLine(food.getCount());
            orderLine.setFood(this.marketService.buyFood(food.getMid(), food.getId(), food.getCount()));
            orderLine.setOrder(order);
        }
        this.orderRepository.save(order);

        return new CreateOrderResponse(order.getId(), this.paymentServiceFactory.getService(request.getPaymentMethod()).ready(order));
    }

    public void cancelOrder(String email, long id) {
        Order order = findOrder(id);
        if(!order.getOrderer().getEmail().equals(email)) {
            throw new DifferentOwnerException(Order.class, email);
        }
        this.paymentServiceFactory.getService(order.getPayment().getPaymentMethod()).refund(order.getPayment());
        order.cancel();
    }

    private Order findOrder(long id) {
        return this.orderRepository.findFetchById(id).orElseThrow(() -> new EntityNotFoundException(Order.class, String.valueOf(id)));
    }
}
