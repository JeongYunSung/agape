package com.yunseong.core.payment.controller;

import com.yunseong.core.payment.KakaoPayOrderVO;
import com.yunseong.core.payment.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final KakaoPayService paymentService;

    @GetMapping("/kakaoPayCancel/{id}")
    public ResponseEntity<?> kakaoPayCancel(@PathVariable long id) {
        KakaoPayOrderVO kakaoPayOrderVO = paymentService.order(id);
        if (kakaoPayOrderVO.getStatus().equals("CANCEL_PAYMENT")) {
            this.paymentService.refund(id);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/kakaoPayFailure/{id}")
    public ResponseEntity<?> kakaoPayFailure(@PathVariable long id) {
        this.paymentService.cancel(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/kakaoPaySuccess/{id}")
    public ResponseEntity<?> kakaoPaySuccess(@PathVariable long id,
                                             @RequestParam("pg_token") String pg_token) {
        this.paymentService.approve(id, pg_token);
        return ResponseEntity.noContent().build();
    }
}
