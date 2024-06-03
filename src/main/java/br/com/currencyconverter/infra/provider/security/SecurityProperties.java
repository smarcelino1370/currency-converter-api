package br.com.currencyconverter.infra.provider.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Base64;

@RequiredArgsConstructor
@ConfigurationProperties(prefix = "security.jwt.token")
public class SecurityProperties {

    private final String secretkey;
    private final long expiresat;


    public String getSecretKey() {
        return Base64.getEncoder().encodeToString(secretkey.getBytes());
    }

    public long getExpiresAt() {
        return expiresat;
    }
}
