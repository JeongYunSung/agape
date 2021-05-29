package com.yunseong.core.member.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MemberOAuth2Test {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberDetailsService memberDetailsService;

    @Test
    void sut_signup_oauth2() throws Exception {
        given(this.memberDetailsService.loadUserByUsername(isA(String.class)))
                .willReturn(User
                        .builder()
                        .username("123dbstjd@naver.com")
                        .password(this.passwordEncoder.encode("password"))
                        .authorities(new SimpleGrantedAuthority("DEFAULT"))
                        .build());

        var sut = this.mockMvc
                .perform(post("/oauth/token")
                        .with(httpBasic("agape", "secret"))
                        .param("username", "123dbstjd@naver.com")
                        .param("password", "password")
                        .param("grant_type", "password")
                        .param("scope", "read,write"));
        sut
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());

        Jackson2JsonParser parser = new Jackson2JsonParser();
        String accessToken = parser.parseMap(sut.andReturn().getResponse().getContentAsString()).get("access_token").toString();

        var actual = this.mockMvc
                .perform(get("/testpage")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken));

        actual
                .andExpect(status().is(404));
    }

    @Test
    @WithMockUser
    void sut_mock_oauth2_test() throws Exception {
        var actual = this.mockMvc
                .perform(get("/testpage"));

        actual
                .andExpect(status().is(404));
    }
}
