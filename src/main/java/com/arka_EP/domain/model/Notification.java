
package com.arka_EP.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {
    private Long id;
    private Long customerId;
    private String message;
    private String type;
    private LocalDateTime sentAt;
    private Boolean read;
}
