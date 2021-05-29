package com.yunseong.core.payment.controller.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoPay {

    private String host;
    private String cancelUri;
    private String successUri;
    private String successFailUri;
}
