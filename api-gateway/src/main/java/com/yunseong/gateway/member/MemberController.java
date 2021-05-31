package com.yunseong.gateway.member;

import com.yunseong.core.member.CreateMemberRequest;
import com.yunseong.core.member.CreateMemberResponse;
import com.yunseong.core.member.ProfileMemberResponse;
import com.yunseong.gateway.config.GatewayConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
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
                .uri("/members/login")
                .retrieve()
                .toEntity(Boolean.class)
                .block();
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile() {
        return this.webClient
                .get()
                .uri("/members/profile")
                .retrieve()
                .toEntity(ProfileMemberResponse.class)
                .block();
    }

    @PostMapping("/signUp")
    public ResponseEntity<CreateMemberResponse> signUp(@RequestBody CreateMemberRequest request) {
        return this.webClient
                .post()
                .uri("/members/signUp")
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .toEntity(CreateMemberResponse.class)
                .block();
    }
}
