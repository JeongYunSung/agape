package com.yunseong.core.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateNotificationResponse {

    @JsonIgnore
    private final long id;
    private final String title;
    private final String description;
}
