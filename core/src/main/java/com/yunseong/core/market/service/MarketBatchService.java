package com.yunseong.core.market.service;

import com.yunseong.core.market.domain.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class MarketBatchService {

    private final MarketRepository marketRepository;

    public void batchUpdateMarket(LocalDate date) {
        this.marketRepository.batchUpdateMarket(date);
    }
}
