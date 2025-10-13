package com.arka_EP.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@ConfigurationProperties(prefix = "security.jwt")
@Data
public class JwtProperties {
    private String secret = "changeit_to_secure_random_value_at_prod";
    private long expirationMs = 3600000; // 1h
}
