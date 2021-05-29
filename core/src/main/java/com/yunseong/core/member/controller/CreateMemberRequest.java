package com.yunseong.core.member.controller;

import com.yunseong.core.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreateMemberRequest {

    private String email;
    private String password;
    private String nickname;

    public Member toMember() {
        return new Member(this.email, this.password, this.nickname);
    }

    public void encode(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(this.password);
    }
}
