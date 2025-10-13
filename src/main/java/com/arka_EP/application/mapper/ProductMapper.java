package com.arka_EP.application.mapper;

import com.arka_EP.application.dto.ProductDto;
import com.arka_EP.domain.model.Product;

public class ProductMapper {
    public static ProductDto toDto(Product p) {
        if (p == null) return null;
        return ProductDto.builder()
            .id(p.getId())
            .sku(p.getSku())
            .name(p.getName())
            .description(p.getDescription())
            .price(p.getPrice())
            .stock(p.getStock())
            .categoryId(p.getCategoryId())
            .build();
    }

    public static Product toDomain(ProductDto d) {
        if (d == null) return null;
        return Product.builder()
            .id(d.getId())
            .sku(d.getSku())
            .name(d.getName())
            .description(d.getDescription())
            .price(d.getPrice())
            .stock(d.getStock())
            .categoryId(d.getCategoryId())
            .build();
    }
}
