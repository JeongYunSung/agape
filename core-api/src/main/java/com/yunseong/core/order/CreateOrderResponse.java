package com.yunseong.core.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseong.core.payment.KakaoPayReadyResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateOrderResponse {

    @JsonIgnore
    private long id;
    private KakaoPayReadyResponse kakaopay;
}
