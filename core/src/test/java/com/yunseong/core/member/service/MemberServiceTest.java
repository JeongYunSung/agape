package com.yunseong.core.member.service;

import com.yunseong.core.member.controller.CreateMemberRequest;
import com.yunseong.core.member.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import static org.assertj.core.api.Assertions.assertThat;

class MemberServiceTest {

    @Test
    void sut_correctly_signup_member() {
        // Arrange
        var stub = new MemberRepositoryStub();
        var encode = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        var sut = new MemberService(encode, stub);
        var request = new CreateMemberRequest("123dbstjd@naver.com", "password", "jys");
        Member member = new Member("test", "test", "test");
        stub.save(member);
        // Act
        var target = stub.findById(1L).get();
        var actual = sut.signUp(request);
        // Assert
        assertThat(target.getEmail()).isEqualTo("test");
        assertThat(actual.getEmail()).isEqualTo(request.getEmail());
        assertThat(actual.getEmail()).isNotEqualTo(target.getEmail());
    }
}