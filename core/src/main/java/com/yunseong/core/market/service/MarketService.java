package com.yunseong.core.market.service;

import com.yunseong.core.common.exception.EntityNotFoundException;
import com.yunseong.core.common.exception.UnsupportedStateTransitionException;
import com.yunseong.core.market.controller.vo.*;
import com.yunseong.core.market.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MarketService {

    private final MarketRepository marketRepository;

    @Transactional(readOnly = true)
    public Page<MarketVO> findMarkets(Pageable pageable) {
        return this.marketRepository.findAll(pageable).map(MarketVO::new);
    }

    @Transactional(readOnly = true)
    public FindMarketDetailResponse findMarket(long id) {
        Market market = this.getFetchMarket(id);
        return new FindMarketDetailResponse(market);
    }

    public void updateMarket(long id, MarketVO marketVO) {
        this.getMarket(id).updateMarket(marketVO);
    }

    public void deleteMarket(long id) {
        this.getMarket(id).deleteMarket();
    }

    public CreateMarketResponse openMarket(CreateMarketRequest request) {
        Restaurant restaurant = request.getRestaurant().toRestaurant();
        Market market = request.getMarket().toMarket();

        request.getFoods().stream().map(FoodVO::toFood).forEach(market::addFood);
        market.registerRestaurant(restaurant);

        this.marketRepository.save(market);

        return new CreateMarketResponse(market);
    }

    @Transactional(readOnly = true)
    public Food buyFood(long id, long fid, int count) {
        return this.getAvailableStateMarket(id).buyFood(fid, count);
    }

    private Market getFetchMarket(long id) {
        return this.marketRepository.findFetchById(id).orElseThrow(() -> new EntityNotFoundException(Market.class, String.valueOf(id)));
    }

    public void updateRestaurant(long id, RestaurantVO restaurantVO) {
        getAvailableStateMarket(id).updateRestaurant(restaurantVO);
    }

    public void addFood(long id, FoodVO food) {
        getAvailableStateMarket(id).addFood(food.toFood());
    }

    public void updateFood(long id, long fid, FoodVO food) {
        getAvailableStateMarket(id).updateFood(fid, food);
    }

    public void deleteFood(long id, long fid) {
        getAvailableStateMarket(id).deleteFood(fid);
    }

    private Market getAvailableStateMarket(long id) {
        Market market = this.getFetchMarket(id);
        if (market.getMarketState() != MarketState.OPENED) {
            throw new UnsupportedStateTransitionException(market.getMarketState());
        }
        return market;
    }

    private Market getMarket(long id) {
        return this.marketRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Market.class, String.valueOf(id)));
    }
}
