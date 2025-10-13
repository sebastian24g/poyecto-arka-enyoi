package com.arka_EP.domain.repository;

import com.arka_EP.domain.model.Payment;
import java.util.Optional;

public interface PaymentRepository {
    Payment save(Payment p);
    Optional<Payment> findById(Long id);
}
