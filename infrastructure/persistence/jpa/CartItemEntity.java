package com.arka_EP.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name="cart_items")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CartItemEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal unitPrice;
}
