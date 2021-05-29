package com.yunseong.core.notification.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunseong.core.notification.domain.Notification;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class FindNotificationResponse {

    private String email;
    private String title;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime createdTime;

    public FindNotificationResponse(Notification notification) {
        this.email = notification.getReceiver().getEmail();
        this.title = notification.getTitle();
        this.description = notification.getDescription();
        this.createdTime = notification.getCreatedTime();
    }
}
