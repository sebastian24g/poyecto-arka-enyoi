
package com.arka_EP.domain.model;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cart {
    private Long id;
    private Long customerId;
    private LocalDateTime createdAt;
    private String status;
    private List<CartItem> items;
}
