package com.yunseong.core.payment.config;

import com.yunseong.core.payment.KakaoPay;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "payment")
public class PaymentConfiguration {

    private KakaoPay kakaopay;
}
