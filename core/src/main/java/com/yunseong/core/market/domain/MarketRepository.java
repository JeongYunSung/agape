package com.yunseong.core.market.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {

    @Modifying
    @Query("update Market m set m.marketState = " +
            "case " +
                "when m.marketState = com.yunseong.core.market.domain.MarketState.WAITING then 'OPENED' " +
                "when m.marketState = com.yunseong.core.market.domain.MarketState.OPENED and m.currentAmount >= m.targetAmount then 'CLOSED' " +
                "else 'CANCELED' " +
            "end " +
            "where m.endTime <= :dateTime " +
                "and m.marketState in (com.yunseong.core.market.domain.MarketState.WAITING, com.yunseong.core.market.domain.MarketState.OPENED)")
    void batchUpdateMarket(LocalDateTime dateTime);

    @Query("select distinct m from Market m inner join fetch m.foods inner join fetch m.restaurant where m.id = :id and m.marketState <> 'DELETE'")
    Optional<Market> findFetchById(long id);

    @Query("select m from Market m where m.id = :id and m.marketState <> 'DELETE'")
    Optional<Market> findById(long id);

    @Query(value = "select m from Market m where m.marketState <> 'DELETE'", countQuery = "select count(m) from Market m where m.marketState <> 'DELETE'")
    Page<Market> findAll(Pageable pageable);
}
