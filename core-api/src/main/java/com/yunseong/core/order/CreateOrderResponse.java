package com.yunseong.core.order;

import com.yunseong.core.payment.KakaoPayReadyVO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateOrderResponse {

    private long id;
    private KakaoPayReadyVO kakaopay;
}