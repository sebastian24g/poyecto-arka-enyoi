package com.arka_EP.application.dto;

import lombok.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductDto {
    private Long id;

    @NotBlank
    private String sku;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;

    @NotNull
    @Min(0)
    private Integer stock;

    private Long categoryId;
}
