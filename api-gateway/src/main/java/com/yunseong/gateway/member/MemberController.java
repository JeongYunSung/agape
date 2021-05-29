package com.yunseong.gateway.member;

import com.yunseong.core.member.ProfileMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MemberController {

    private final WebClient webClient;

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn() {
        return this.webClient
                .get()
                .uri("/members/profile")
                .retrieve()
                .toEntity(ProfileMemberResponse.class)
                .block();
    }
}