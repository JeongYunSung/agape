package com.yunseong.core.member.service;

import com.yunseong.core.common.exception.EntityNotFoundException;
import com.yunseong.core.member.ProfileMemberResponse;
import com.yunseong.core.member.domain.Member;
import com.yunseong.core.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = findMember(email);

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .authorities(new SimpleGrantedAuthority("DEFAULT"))
                .build();
    }

    public Member findMember(String email) {
        return this.memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(Member.class, email));
    }

    public ProfileMemberResponse profile(String email) {
        Member member = this.findMember(email);
        return new ProfileMemberResponse(member.getEmail(), member.getNickname(), member.getCreatedTime());
    }
}
