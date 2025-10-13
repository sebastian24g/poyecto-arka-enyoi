package com.arka_EP.application.service;

import com.arka_EP.domain.model.Product;
import com.arka_EP.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAppService {
    private final ProductRepository productRepository;

    public Product create(Product p) {
        return productRepository.save(p);
    }

    public Product get(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found"));
    }

    public List<Product> lowStock(int threshold) {
        return productRepository.findLowStock(threshold);
    }
}
