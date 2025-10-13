package com.arka_EP.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="payments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private BigDecimal amount;
    private String method;
    private String status;
    private LocalDateTime createdAt;
}
