package com.yunseong.core.market.scheduler;

import com.yunseong.core.market.service.MarketBatchService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class MarketScheduler {

    private final MarketBatchService marketBatchService;

    @Scheduled(cron = "0 */2 * * * *")
//    @Scheduled(cron = "0 0 0 * * *")
    public void task() {
        this.marketBatchService.batchUpdateMarket(LocalDate.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
    }
}
