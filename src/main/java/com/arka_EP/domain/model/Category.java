
package com.arka_EP.domain.model;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Category {
    private Long id;
    private String name;
    private String description;
}
