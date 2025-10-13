
package com.arka_EP.domain.repository;

import com.arka_EP.domain.model.Cart;
import java.util.Optional;

public interface CartRepository {
    Cart save(Cart c);
    Optional<Cart> findById(Long id);
}
