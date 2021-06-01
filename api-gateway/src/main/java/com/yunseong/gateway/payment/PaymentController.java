package com.yunseong.gateway.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping(value = "/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final WebClient webClient;

    @GetMapping("/kakaoPayCancel")
    public ResponseEntity<?> kakaoPayCancel(@PathVariable long id) {
        return this.webClient
                .get()
                .uri("http://gateway/kakaoPayCancel/" + id)
                .retrieve()
                .toBodilessEntity().block();
    }

    @GetMapping("/kakaoPayFailure")
    public ResponseEntity<?> kakaoPayFailure(@PathVariable long id) {
        return this.webClient
                .get()
                .uri("http://gateway/kakaoPayFailure/" + id)
                .retrieve()
                .toBodilessEntity().block();
    }

    @GetMapping("/kakaoPaySuccess")
    public ResponseEntity<?> kakaoPaySuccess(@PathVariable long id,
                                             @RequestParam("pg_token") String pg_token) {
        return this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("http://gateway/kakaoPaySuccess/" + id)
                        .queryParam("pg_token", pg_token)
                        .build())
                .retrieve()
                .toBodilessEntity().block();
    }
}
