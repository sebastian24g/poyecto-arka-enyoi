package com.arka_EP.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name="notifications")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class NotificationEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    @Column(columnDefinition="text")
    private String message;
    private String type;
    private LocalDateTime sentAt;
    private Boolean read;
}
