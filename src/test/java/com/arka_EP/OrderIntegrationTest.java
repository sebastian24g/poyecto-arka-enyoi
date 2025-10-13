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
import com.arka_EP.application.dto.PlaceOrderCommand;
import com.arka_EP.application.dto.PlaceOrderCommand.Item;
import java.math.BigDecimal;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderIntegrationTest {

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
    void placeOrderFlow() {
        // register user
        var reg = new com.arka_EP.application.dto.RegisterUserDto(null, "buyer", "bpass", "b@x.com");
        restTemplate.postForEntity("/api/auth/register", reg, Object.class);

        var authReq = new com.arka_EP.application.dto.AuthRequest("buyer", "bpass");
        ResponseEntity<com.arka_EP.application.dto.AuthResponse> authResp = restTemplate.postForEntity("/api/auth/login", authReq, com.arka_EP.application.dto.AuthResponse.class);
        String token = authResp.getBody().getToken();

        // create product as admin - seed contains admin user but token needed; skip admin creation: fetch token for admin by logging admin (admin:admin123 seeded)
        var adminAuth = new com.arka_EP.application.dto.AuthRequest("admin", "admin123");
        ResponseEntity<com.arka_EP.application.dto.AuthResponse> adminAuthResp = restTemplate.postForEntity("/api/auth/login", adminAuth, com.arka_EP.application.dto.AuthResponse.class);
        String adminToken = adminAuthResp.getBody().getToken();

        ProductDto dto = ProductDto.builder().sku("SKU-ORDER").name("ProdOrder").price(new BigDecimal("5.0")).stock(50).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductDto> req = new HttpEntity<>(dto, headers);
        ResponseEntity<ProductDto> createResp = restTemplate.postForEntity("/api/products", req, ProductDto.class);
        assertThat(createResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        Long productId = createResp.getBody().getId();

        // place order
        PlaceOrderCommand cmd = new PlaceOrderCommand();
        cmd.setCustomerId(1L); // simple
        Item item = new Item(productId, 2, new BigDecimal("5.0"));
        cmd.setItems(List.of(item));

        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setBearerAuth(token);
        userHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PlaceOrderCommand> orderReq = new HttpEntity<>(cmd, userHeaders);
        ResponseEntity<Long> orderResp = restTemplate.postForEntity("/api/orders", orderReq, Long.class);
        assertThat(orderResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(orderResp.getBody()).isNotNull();
    }
}
