package com.yunseong.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.core.http.converter.OAuth2ErrorHttpMessageConverter;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ResourceServerConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/").anonymous()
                        .antMatchers(HttpMethod.POST, "/members/signUp").anonymous()
                        .antMatchers(HttpMethod.POST, "/members/signIn").anonymous()
                        .antMatchers(HttpMethod.GET, "/payments/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/.well-known/jwks.json").permitAll()
                        .antMatchers(HttpMethod.GET, "/actuator/health").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
//                        oauth2.opaqueToken(token ->
//                                token
//                                        .introspectionClientCredentials("agape", "secret")
//                                        .introspectionUri("http://localhost:8090/oauth/check_token")));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new OAuth2ErrorHttpMessageConverter());
        restTemplate.getMessageConverters().add(new OAuth2AccessTokenResponseHttpMessageConverter());

        return restTemplate;
    }
}