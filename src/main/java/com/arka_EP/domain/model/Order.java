
package com.arka_EP.domain.model;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Order {
    private Long id;
    private Long customerId;
    private LocalDateTime createdAt;
    private String status;
    private BigDecimal total;
    private List<OrderItem> items;
}
