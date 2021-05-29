package com.yunseong.core.member.service;

import com.yunseong.core.member.CreateMemberRequest;
import com.yunseong.core.member.CreateMemberResponse;
import com.yunseong.core.member.domain.Member;
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
        Member member = this.memberRepository.save(new Member(request.getEmail(), this.passwordEncoder.encode(request.getPassword()), request.getNickname()));
        return new CreateMemberResponse(member.getId(), member.getEmail(), member.getNickname(), member.getCreatedTime());
    }
}
