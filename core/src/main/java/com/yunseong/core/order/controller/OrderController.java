package com.yunseong.core.order.controller;

import com.yunseong.core.order.CreateOrderRequest;
import com.yunseong.core.order.CreateOrderResponse;
import com.yunseong.core.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "orders", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable long id,
                                         JwtAuthenticationToken principal) {
        this.orderService.cancelOrder((String) principal.getTokenAttributes().get("user_name"), id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<?> requestOrder(JwtAuthenticationToken principal,
                                          @RequestBody CreateOrderRequest request) {
        CreateOrderResponse response = this.orderService.requestOrder((String) principal.getTokenAttributes().get("user_name"), request);
        return ResponseEntity
                .created(URI.create("/" + response.getId()))
                .body(response);
    }
}
