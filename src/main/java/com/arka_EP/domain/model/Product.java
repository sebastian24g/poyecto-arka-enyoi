
package com.arka_EP.domain.model;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Product {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Long categoryId;

    public void decreaseStock(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("qty>0");
        if (this.stock - qty < 0) throw new IllegalStateException("Insufficient stock");
        this.stock -= qty;
    }
}
