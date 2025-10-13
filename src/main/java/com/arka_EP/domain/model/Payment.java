
package com.arka_EP.domain.model;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {
    private Long id;
    private Long orderId;
    private BigDecimal amount;
    private String method;
    private String status;
    private LocalDateTime createdAt;
}
