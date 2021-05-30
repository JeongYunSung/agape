package com.yunseong.core.payment;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AmountVO {

    private Integer total, tax_free, vat, point, discount;
}
