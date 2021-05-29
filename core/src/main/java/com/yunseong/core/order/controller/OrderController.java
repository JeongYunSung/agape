package com.yunseong.core.order.controller;

import com.yunseong.core.order.CreateOrderRequest;
import com.yunseong.core.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable long id,
                                         @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        this.orderService.cancelOrder(principal.getAttribute("user_name"), id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<?> requestOrder(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal,
                                          @RequestBody CreateOrderRequest request) {
        return ResponseEntity
                .created(URI.create("/" + this.orderService.requestOrder(principal.getAttribute("user_name"), request).getId()))
                .build();
    }
}
