package com.yunseong.core.notification.controller;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateNotificationRequest {

    private String title;
    private String description;
}
