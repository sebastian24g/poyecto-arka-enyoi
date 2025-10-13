package com.arka_EP.infrastructure.persistence.adapter;

import com.arka_EP.domain.model.Customer;
import com.arka_EP.domain.repository.CustomerRepository;
import com.arka_EP.infrastructure.persistence.jpa.CustomerEntity;
import com.arka_EP.infrastructure.persistence.jpa.SpringDataCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepository {
    private final SpringDataCustomerRepository springRepo;

    @Override
    public Optional<Customer> findById(Long id) {
        return springRepo.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return springRepo.findByEmail(email).map(this::toDomain);
    }

    @Override
    public Customer save(Customer c) {
        CustomerEntity e = CustomerEntity.builder()
                .id(c.getId())
                .name(c.getName())
                .email(c.getEmail())
                .phone(c.getPhone())
                .build();
        var saved = springRepo.save(e);
        return toDomain(saved);
    }

    private Customer toDomain(CustomerEntity e) {
        return Customer.builder().id(e.getId()).name(e.getName()).email(e.getEmail()).phone(e.getPhone()).build();
    }
}
