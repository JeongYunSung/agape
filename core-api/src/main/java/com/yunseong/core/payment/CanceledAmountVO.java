package com.yunseong.core.payment;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CanceledAmountVO {

    private int total, tax_free, vat, point, discount;
}
