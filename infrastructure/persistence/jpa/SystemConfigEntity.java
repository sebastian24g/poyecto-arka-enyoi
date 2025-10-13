package com.arka_EP.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="system_config")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SystemConfigEntity {
    @Id
    private String key;
    @Column(columnDefinition="text")
    private String value;
}
