package com.yunseong.core.market.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunseong.core.market.domain.Market;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MarketVO {

    private String title;
    private String description;
    private String location;
    private int currentAmount;
    private int targetAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endTime;

    public MarketVO(Market market) {
        this.title = market.getTitle();
        this.description = market.getDescription();
        this.location = market.getLocation();
        this.currentAmount = market.getCurrentAmount();
        this.targetAmount = market.getTargetAmount();
        this.startTime = market.getStartTime();
        this.endTime = market.getEndTime();
    }

    public Market toMarket() {
        return new Market(this.title, this.description, this.location, this.currentAmount, this.targetAmount, this.startTime, this.endTime);
    }
}
