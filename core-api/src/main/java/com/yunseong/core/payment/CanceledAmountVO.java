package com.yunseong.core.payment;

import lombok.Data;

@Data
public class CanceledAmountVO {

    private int total, tax_free, vat, point, discount;
}
