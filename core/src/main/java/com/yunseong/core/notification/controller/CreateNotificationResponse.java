package com.yunseong.core.notification.controller;

import com.yunseong.core.notification.domain.Notification;
import lombok.Getter;
import net.minidev.json.annotate.JsonIgnore;

@Getter
public class CreateNotificationResponse {

    @JsonIgnore
    private long id;
    private String title;
    private String description;

    public CreateNotificationResponse(Notification notification) {
        this.id = notification.getId();
        this.title = notification.getTitle();
        this.description = notification.getDescription();
    }
}
