package com.yunseong.core.config;

import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.Map;

public class JwtCustomHeadersAccessTokenConverter extends JwtAccessTokenConverter {

    private final Map<String, String> customHeaders;
    private final JsonParser objectMapper = JsonParserFactory.create();
    private final RsaSigner rsaSigner;

    public JwtCustomHeadersAccessTokenConverter(Map<String, String> customHeaders, KeyPair keyPair) {
        this.customHeaders = customHeaders;
        this.rsaSigner = new RsaSigner((RSAPrivateKey) keyPair.getPrivate());

    }

    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String content;
        try {
            content = this.objectMapper.formatMap(getAccessTokenConverter().convertAccessToken(accessToken, authentication));
        }
        catch (Exception e) {
            throw new IllegalStateException("Cannot convert access token to JSON", e);
        }
        String token = JwtHelper.encode(content, this.rsaSigner, this.customHeaders).getEncoded();
        return token;
    }
}
