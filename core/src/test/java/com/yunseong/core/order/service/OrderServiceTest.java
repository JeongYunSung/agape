package com.yunseong.core.order.service;

import com.yunseong.core.DomainArgumentSource;
import com.yunseong.core.common.exception.FoodSoldOutException;
import com.yunseong.core.common.exception.UnsupportedStateTransitionException;
import com.yunseong.core.market.CreateMarketRequest;
import com.yunseong.core.market.service.MarketRepositoryStub;
import com.yunseong.core.market.service.MarketService;
import com.yunseong.core.member.domain.Member;
import com.yunseong.core.member.service.MemberDetailsService;
import com.yunseong.core.member.service.MemberRepositoryStub;
import com.yunseong.core.order.controller.vo.CreateOrderRequest;
import com.yunseong.core.order.controller.vo.CreateOrderResponse;
import com.yunseong.core.order.controller.vo.FoodVO;
import com.yunseong.core.order.domain.Order;
import com.yunseong.core.order.domain.OrderState;
import com.yunseong.core.payment.service.PaymentServiceFactory;
import org.junit.jupiter.params.ParameterizedTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderServiceTest {

    @ParameterizedTest
    @DomainArgumentSource
    void sut_failure_request_order_closed_market_payment_by_kakaopay(CreateMarketRequest marketRequest, CreateOrderRequest orderRequest) {
        // Arrange
        var member_stub = new MemberRepositoryStub();
        var memberDetailsServer = new MemberDetailsService(member_stub);
        var market_stub = new MarketRepositoryStub();
        var marketService = new MarketService(market_stub);
        var kakaoPayService = new KakaoPayServiceStub();
        var paymentServiceFactory = new PaymentServiceFactory(kakaoPayService);

        var order_stub = new OrderRepositoryStub();
        var sut = new OrderService(order_stub, memberDetailsServer, marketService, paymentServiceFactory);

        var member = new Member("123dbstjd@naver.com", "password", "jys");

        marketService.openMarket(marketRequest);
        member_stub.save(member);

        // Act
        // Assert
        assertThrows(UnsupportedStateTransitionException.class, () -> {
            sut.requestOrder(member.getEmail(), orderRequest);
        });
    }

    @ParameterizedTest
    @DomainArgumentSource
    void sut_correctly_request_order_opened_market_payment_by_kakaopay(CreateMarketRequest marketRequest, CreateOrderRequest orderRequest) {
        // Arrange
        var member_stub = new MemberRepositoryStub();
        var memberDetailsServer = new MemberDetailsService(member_stub);
        var market_stub = new MarketRepositoryStub();
        var marketService = new MarketService(market_stub);
        var kakaoPayService = new KakaoPayServiceStub();
        var paymentServiceFactory = new PaymentServiceFactory(kakaoPayService);

        var order_stub = new OrderRepositoryStub();
        var sut = new OrderService(order_stub, memberDetailsServer, marketService, paymentServiceFactory);

        var member = new Member("123dbstjd@naver.com", "password", "jys");

        marketService.openMarket(marketRequest);
        member_stub.save(member);

        market_stub.batchUpdateMarket(LocalDate.of(2021, 7, 1));
        // Act
        CreateOrderResponse response = sut.requestOrder(member.getEmail(), orderRequest);
        Optional<Order> actual = order_stub.findById(response.getId());
        // Assert
        assertThat(actual).isPresent();
        assertThat(actual.get().getOrderState()).isEqualTo(OrderState.ORDER_PROCESSING);
    }

    @ParameterizedTest
    @DomainArgumentSource
    void sut_failure_request_order_food_sold_out_payment_by_kakaopay(CreateMarketRequest marketRequest, CreateOrderRequest orderRequest) {
        // Arrange
        var member_stub = new MemberRepositoryStub();
        var memberDetailsServer = new MemberDetailsService(member_stub);
        var market_stub = new MarketRepositoryStub();
        var marketService = new MarketService(market_stub);
        var kakaoPayService = new KakaoPayServiceStub();
        var paymentServiceFactory = new PaymentServiceFactory(kakaoPayService);

        var order_stub = new OrderRepositoryStub();
        var sut = new OrderService(order_stub, memberDetailsServer, marketService, paymentServiceFactory);

        var member = new Member("123dbstjd@naver.com", "password", "jys");

        marketService.openMarket(marketRequest);
        member_stub.save(member);
        orderRequest.getFoods().add(new FoodVO(1, 1, 1000000));

        market_stub.batchUpdateMarket(LocalDate.of(2021, 7, 1));
        // Act
        // Assert
        assertThrows(FoodSoldOutException.class, () -> {
            sut.requestOrder(member.getEmail(), orderRequest);
        });
    }
}