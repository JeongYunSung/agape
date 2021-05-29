package com.yunseong.core.order;

import com.yunseong.core.payment.KakaoPayReadyVO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateOrderResponse {

    private long id;
    private KakaoPayReadyVO kakaopay;
}
