package com.yunseong.core.member.controller;

import com.yunseong.core.member.CreateMemberRequest;
import com.yunseong.core.member.CreateMemberResponse;
import com.yunseong.core.member.service.MemberDetailsService;
import com.yunseong.core.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "members", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberDetailsService memberDetailsService;

    @GetMapping("/login")
    public ResponseEntity<?> login(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        return ResponseEntity.ok(principal != null && (principal.getAttribute("user_name") != null));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        return ResponseEntity.ok(this.memberDetailsService.profile(principal == null ? "admin" : principal.getAttribute("user_name")));
    }

    @PostMapping(value = "/signUp", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@RequestBody CreateMemberRequest request) {
        CreateMemberResponse member = this.memberService.signUp(request);
        return ResponseEntity
                .created(URI.create("/profile/" + member.getId()))
                .body(member);
    }
}
