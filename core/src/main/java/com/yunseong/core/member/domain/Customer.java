package com.yunseong.core.member.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;

public class Customer extends User {

    private String tId;

    public Customer(String username, String password, GrantedAuthority...grantedAuthorities) {
        super(username, password, Arrays.asList(grantedAuthorities));
    }

    public void registerKakaoPay(String tId) {
        this.tId = tId;
    }

    public String getKakaoPayId() {
        return this.tId;
    }

    public static CustomerBuicorer customerBuicorer() {
        return new CustomerBuicorer();
    }

    public static final class CustomerBuicorer {

        private String username;
        private String password;
        private GrantedAuthority[] authorities;

        private CustomerBuicorer() {}

        public CustomerBuicorer username(String username) {
            this.username = username;
            return this;
        }

        public CustomerBuicorer password(String password) {
            this.password = password;
            return this;
        }

        public CustomerBuicorer authorities(GrantedAuthority...grantedAuthorities) {
            this.authorities = grantedAuthorities;
            return this;
        }

        public Customer build() {
            return new Customer(this.username, this.password, this.authorities);
        }
    }
}
