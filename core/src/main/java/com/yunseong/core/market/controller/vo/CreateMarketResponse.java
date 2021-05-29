package com.yunseong.core.market.controller.vo;

import com.yunseong.core.market.domain.Market;
import lombok.Getter;
import net.minidev.json.annotate.JsonIgnore;

@Getter
public class CreateMarketResponse {

    @JsonIgnore
    private long id;
    private String title;
    private String description;

    public CreateMarketResponse(Market market) {
        this.id = market.getId();
        this.title = market.getTitle();
        this.description = market.getDescription();
    }
}
