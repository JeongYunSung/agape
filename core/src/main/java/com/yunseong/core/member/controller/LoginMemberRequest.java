package com.yunseong.core.member.controller;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginMemberRequest {

    private String email;
    private String password;
}
