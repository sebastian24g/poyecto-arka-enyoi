
package com.arka_EP.domain.repository;

import com.arka_EP.domain.model.Product;
import java.util.Optional;
import java.util.List;

public interface ProductRepository {
    Optional<Product> findById(Long id);
    Optional<Product> findBySku(String sku);
    Product save(Product p);
    List<Product> findLowStock(int threshold);
    void decreaseStock(Long id, int qty);
}
