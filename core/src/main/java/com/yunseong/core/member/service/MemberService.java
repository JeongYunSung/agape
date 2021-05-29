package com.yunseong.core.member.service;

import com.yunseong.core.member.controller.CreateMemberRequest;
import com.yunseong.core.member.controller.CreateMemberResponse;
import com.yunseong.core.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public CreateMemberResponse signUp(CreateMemberRequest request) {
        request.encode(this.passwordEncoder);
        return new CreateMemberResponse(this.memberRepository.save(request.toMember()));
    }
}
