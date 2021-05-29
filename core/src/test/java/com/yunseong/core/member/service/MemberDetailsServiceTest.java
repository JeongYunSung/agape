package com.yunseong.core.member.service;

import com.yunseong.core.member.domain.Member;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class MemberDetailsServiceTest {

    @ParameterizedTest
    @CsvSource({"123dbstjd@naver.com, password, jys"})
    void sut_correctly_returns_customer(String id, String password, String name) {
        // Arrange
        var stub = new MemberRepositoryStub();
        var sut = new MemberDetailsService(stub);
        Member member = new Member(id, password, name);
        stub.save(member);
        // Act
        var actual = sut.loadUserByUsername(id);
        // Assert
        assertThat(actual.getClass().getSimpleName()).isEqualTo("User");
        assertThat(actual.getUsername()).isEqualTo(id);
    }
}