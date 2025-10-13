package com.arka_EP;

import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthIntegrationTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private TestRestTemplate restTemplate;

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void registerAndLogin() {
        var reg = new com.arka_EP.application.dto.RegisterUserDto(null, "testuser", "testpass", "test@x.com");
        ResponseEntity<Object> regResp = restTemplate.postForEntity("/api/auth/register", reg, Object.class);
        assertThat(regResp.getStatusCode()).isEqualTo(HttpStatus.OK);

        var authReq = new com.arka_EP.application.dto.AuthRequest("testuser", "testpass");
        ResponseEntity<com.arka_EP.application.dto.AuthResponse> authResp = restTemplate.postForEntity("/api/auth/login", authReq, com.arka_EP.application.dto.AuthResponse.class);
        assertThat(authResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(authResp.getBody().getToken()).isNotBlank();
    }
}
