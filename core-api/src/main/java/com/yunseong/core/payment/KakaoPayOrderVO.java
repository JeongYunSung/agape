package com.yunseong.core.payment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoPayOrderVO {

    private String tid, cid, status, partner_order_id, partner_user_id, payment_method_type, item_name, item_code;
    private int quantity;
    private AmountVO amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime created_at;
}
