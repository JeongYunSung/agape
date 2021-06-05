package com.yunseong.gateway.order;

import com.yunseong.core.order.CreateOrderRequest;
import com.yunseong.core.order.CreateOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderController {

    private final WebClient webClient;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable long id) {
        return this.webClient
                .get()
                .uri("/orders/" + id)
                .retrieve()
                .toBodilessEntity().block();
    }

    @PostMapping
    public ResponseEntity<?> requestOrder(@RequestBody CreateOrderRequest request) {
        return this.webClient
                .post()
                .uri("/orders")
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .toEntity(CreateOrderResponse.class)
                .block();
    }
}
