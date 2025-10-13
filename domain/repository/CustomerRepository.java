package com.arka_EP.domain.repository;

import com.arka_EP.domain.model.Customer;
import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findById(Long id);
    Optional<Customer> findByEmail(String email);
    Customer save(Customer c);
}
