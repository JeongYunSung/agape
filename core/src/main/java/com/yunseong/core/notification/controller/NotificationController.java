package com.yunseong.core.notification.controller;

import com.yunseong.core.config.PageMetadata;
import com.yunseong.core.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/notifications", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

//    @PostMapping
//    public ResponseEntity<?> sendNotification(@AuthenticationPrincipal Customer customer,
//                                                @RequestBody NotificationCreateRequest request) {
//        NotificationCreateResponse notification = this.notificationService.sendNotification(customer.getUsername(), request);
//        return ResponseEntity
//                .created(URI.create("/" + notification.getId()))
//                .body(notification);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findNotification(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal,
                                              @PathVariable long id) {
        return ResponseEntity.ok(this.notificationService.findNotification(principal.getAttribute("user_name"), id));
    }

    @GetMapping
    public ResponseEntity<?> findNotifications(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal, @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(new PageMetadata<>(this.notificationService.findNotifications(principal.getAttribute("user_name"), pageable)));
    }
}
