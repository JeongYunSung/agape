package com.yunseong.core.member.controller;

import com.yunseong.core.member.domain.Member;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class CreateMemberResponse {

    private long id;
    private String email;
    private String nickname;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdTime;

    public CreateMemberResponse(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.createdTime = member.getCreatedTime();
    }
}
