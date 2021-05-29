package com.yunseong.core.market.service;

import com.yunseong.core.DomainArgumentSource;
import com.yunseong.core.market.controller.vo.CreateMarketRequest;
import com.yunseong.core.market.controller.vo.MarketVO;
import com.yunseong.core.market.domain.MarketState;
import org.junit.jupiter.params.ParameterizedTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class MarketServiceTest {

    @ParameterizedTest
    @DomainArgumentSource
    void sut_correctly_open_market(CreateMarketRequest[] requests) {
        // Arrange
        var stub = new MarketRepositoryStub();
        var sut = new MarketService(stub);
        // Act
        var actual = Arrays.stream(requests)
                .map(sut::openMarket)
                .collect(Collectors.toList());
        // Assert
        assertThat(actual).hasSize(requests.length);
        assertThat(actual).allMatch(x -> x.getTitle().contains("마켓"));
    }

    @ParameterizedTest
    @DomainArgumentSource
    void sut_failure_batch_before_date_expect_state_waiting(CreateMarketRequest[] requests) {
        // Arrange
        var stub = new MarketRepositoryStub();
        var service = new MarketService(stub);
        var sut = new MarketBatchService(stub);
        // Act
        Arrays.stream(requests).forEach(service::openMarket);
        sut.batchUpdateMarket(LocalDate.of(2021, 6, 30));
        var actual = stub.findAll();
        // Assert
        assertThat(actual).allMatch(x -> x.getMarketState() == MarketState.WAITING);
    }

    @ParameterizedTest
    @DomainArgumentSource
    void sut_failure_batch_before_date_expect_state_canceled(CreateMarketRequest[] requests) {
        // Arrange
        var stub = new MarketRepositoryStub();
        var service = new MarketService(stub);
        var sut = new MarketBatchService(stub);
        // Act
        Arrays.stream(requests).forEach(service::openMarket);
        sut.batchUpdateMarket(LocalDate.of(2021, 8, 30));
        var actual = stub.findAll();
        // Assert
        actual.forEach(a -> System.out.println(a.getMarketState()));
        assertThat(actual).allMatch(x -> x.getMarketState() == MarketState.CANCELED);
    }

    @ParameterizedTest
    @DomainArgumentSource
    void sut_failure_batch_before_date_expect_state_opened(CreateMarketRequest[] requests) {
        // Arrange
        var stub = new MarketRepositoryStub();
        var service = new MarketService(stub);
        var sut = new MarketBatchService(stub);
        // Act
        Arrays.stream(requests).forEach(service::openMarket);
        sut.batchUpdateMarket(LocalDate.of(2021, 6, 30));
        var actual = stub.findAll();
        // Assert
        assertThat(actual).allMatch(x -> x.getMarketState() == MarketState.OPENED);
    }

    @ParameterizedTest
    @DomainArgumentSource
    void sut_failure_batch_before_date_expect_state_closed(CreateMarketRequest[] requests) {
        // Arrange
        var stub = new MarketRepositoryStub();
        var service = new MarketService(stub);
        var sut = new MarketBatchService(stub);
        // Act
        Arrays.stream(requests).forEach(service::openMarket);
        stub.findAll().forEach(market -> market.updateMarket(new MarketVO(market.getTitle(), market.getDescription(), market.getLocation(),
                1000000, 50000, market.getStartTime(), market.getEndTime())));
        sut.batchUpdateMarket(LocalDate.of(2021, 8, 30));
        var actual = stub.findAll();
        // Assert
        assertThat(actual).allMatch(x -> x.getMarketState() == MarketState.CLOSED);
    }
}