
package com.arka_EP.domain.model;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CartItem {
    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal unitPrice;
}
