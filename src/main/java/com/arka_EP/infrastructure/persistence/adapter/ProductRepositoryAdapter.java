package com.arka_EP.infrastructure.persistence.adapter;

import com.arka_EP.domain.model.Product;
import com.arka_EP.domain.repository.ProductRepository;
import com.arka_EP.infrastructure.persistence.jpa.ProductEntity;
import com.arka_EP.infrastructure.persistence.jpa.SpringDataProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {
    private final SpringDataProductRepository springRepo;

    @Override
    public Optional<Product> findById(Long id) {
        return springRepo.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        return springRepo.findBySku(sku).map(this::toDomain);
    }

    @Override
    public Product save(Product p) {
        ProductEntity e = ProductEntity.builder()
                .id(p.getId())
                .sku(p.getSku())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .stock(p.getStock())
                .categoryId(p.getCategoryId())
                .build();
        var saved = springRepo.save(e);
        return toDomain(saved);
    }

    @Override
    public List<Product> findLowStock(int threshold) {
        return springRepo.findByStockLessThan(threshold).stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void decreaseStock(Long id, int qty) {
        var e = springRepo.findByIdForUpdate(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        if (e.getStock() < qty) throw new IllegalStateException("Insufficient stock");
        e.setStock(e.getStock() - qty);
        springRepo.save(e);
    }

    private Product toDomain(ProductEntity e) {
        return Product.builder()
                .id(e.getId())
                .sku(e.getSku())
                .name(e.getName())
                .description(e.getDescription())
                .price(e.getPrice())
                .stock(e.getStock())
                .categoryId(e.getCategoryId())
                .build();
    }
}
