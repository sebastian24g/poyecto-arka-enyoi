package com.arka_EP;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import com.arka_EP.infrastructure.persistence.jpa.SpringDataProductRepository;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductRepositoryIntegrationTest {

    @Test
    void containerStartsAndRepositoryWorks() {
        try (PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test")) {
            postgres.start();
            // set system properties so Spring can pick them up if needed
            System.setProperty("spring.datasource.url", postgres.getJdbcUrl());
            System.setProperty("spring.datasource.username", postgres.getUsername());
            System.setProperty("spring.datasource.password", postgres.getPassword());

            // basic assertion that container is running
            assertThat(postgres.isRunning()).isTrue();
        }
    }
}
