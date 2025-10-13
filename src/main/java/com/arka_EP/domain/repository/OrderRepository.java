
package com.arka_EP.domain.repository;

import com.arka_EP.domain.model.Order;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order o);
    Optional<Order> findById(Long id);
}
