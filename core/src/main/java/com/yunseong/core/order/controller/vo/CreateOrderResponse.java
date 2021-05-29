package com.yunseong.core.order.controller.vo;

import com.yunseong.core.payment.controller.vo.KakaoPayReadyVO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateOrderResponse {

    private long id;
    private KakaoPayReadyVO kakaopay;
}
