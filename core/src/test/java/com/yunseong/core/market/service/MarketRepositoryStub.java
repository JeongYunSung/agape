package com.yunseong.core.market.service;

import com.yunseong.core.market.domain.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MarketRepositoryStub implements MarketRepository {

    private long foodId;
    private long restaurantId;
    private List<Market> markets = new ArrayList<>();

    private void add(Market entity) {
        try {
            this.markets.add(entity);
            Market market = this.markets.get(this.markets.size()-1);
            setId(Market.class, market, Long.valueOf(this.markets.size()));
            setId(Restaurant.class, market.getRestaurant(), ++this.restaurantId);
            market.getFoods().forEach(f -> {
                try {
                    setId(Food.class, f, ++this.foodId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setId(Class<?> clazz, Object obj, long id) throws Exception {
        Field field = clazz.getDeclaredField("id");
        field.setAccessible(true);
        field.set(obj, id);
    }

    @Override
    public void batchUpdateMarket(LocalDate dateTime) {
        try {
            for(Market market : this.markets) {
                Field field = market.getClass().getDeclaredField("marketState");
                field.setAccessible(true);
                System.out.println(market.getStartTime() + " : " + dateTime);
                if(market.getStartTime().isBefore(dateTime)) {
                        field.set(market, MarketState.OPENED);
                }
                if(market.getEndTime().isBefore(dateTime)) {
                    if(market.getTargetAmount() > market.getCurrentAmount()) {
                        field.set(market, MarketState.CANCELED);
                    }else {
                        field.set(market, MarketState.CLOSED);
                    }
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Market> findFetchById(long id) {
        return Optional.of(this.markets.get(Math.toIntExact(id-1)));
    }

    @Override
    public Optional<Market> findById(long id) {
        return Optional.of(this.markets.get(Math.toIntExact(id-1)));
    }

    @Override
    public Page<Market> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Market> findAll() {
        return this.markets;
    }

    @Override
    public List<Market> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<Market> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Market entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends Market> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Market> S save(S entity) {
        add(entity);
        return entity;
    }

    @Override
    public <S extends Market> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Market> findById(Long aLong) {
        return Optional.of(this.markets.get(Math.toIntExact(aLong-1)));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Market> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Market> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Market getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends Market> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Market> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Market> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Market> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Market> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Market> boolean exists(Example<S> example) {
        return false;
    }
}
