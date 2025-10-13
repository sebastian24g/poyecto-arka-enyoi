package com.arka_EP.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name="products")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;
    private String name;
    @Column(columnDefinition="text")
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Long categoryId;

    @Version
    private Integer version;
}
