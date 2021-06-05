package com.yunseong.gateway.notification;

import com.yunseong.core.common.PageMetadata;
import com.yunseong.core.notification.FindNotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping(value = "/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class NotificationController {

    private final WebClient webClient;

    @GetMapping("/{id}")
    public ResponseEntity<?> findNotification(@PathVariable long id) {
        return this.webClient
                .get()
                .uri("/notifications/" + id)
                .retrieve()
                .toEntity(FindNotificationResponse.class)
                .block();
    }

    @GetMapping
    public ResponseEntity<?> findNotifications(@PageableDefault Pageable pageable) {
        return this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/notifications")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .queryParam("sort", pageable.getSort() == Sort.unsorted() ? "" : pageable.getSort().toString())
                        .build()
                )
                .retrieve()
                .toEntity(PageMetadata.class)
                .block();
    }
}
