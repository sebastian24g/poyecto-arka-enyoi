package com.arka_EP;

import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import com.arka_EP.application.dto.ProductDto;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegrationTest {

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
    void createAndGetProductRequiresAuth() {
        // First, register a user
        var reg = new com.arka_EP.application.dto.RegisterUserDto(null, "user1", "pass1", "u1@x.com");
        ResponseEntity<Object> regResp = restTemplate.postForEntity("/api/auth/register", reg, Object.class);
        assertThat(regResp.getStatusCode()).isEqualTo(HttpStatus.OK);

        // login
        var authReq = new com.arka_EP.application.dto.AuthRequest("user1", "pass1");
        ResponseEntity<com.arka_EP.application.dto.AuthResponse> authResp = restTemplate.postForEntity("/api/auth/login", authReq, com.arka_EP.application.dto.AuthResponse.class);
        assertThat(authResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        String token = authResp.getBody().getToken();

        // try to create product (should be forbidden for non-admin)
        ProductDto dto = ProductDto.builder().sku("SKU1").name("P1").price(new BigDecimal("10.0")).stock(100).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductDto> req = new HttpEntity<>(dto, headers);
        ResponseEntity<ProductDto> createResp = restTemplate.postForEntity("/api/products", req, ProductDto.class);
        assertThat(createResp.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
