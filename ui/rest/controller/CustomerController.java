package com.arka_EP.ui.rest.controller;

import com.arka_EP.domain.model.Customer;
import com.arka_EP.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer c) {
        return ResponseEntity.ok(customerRepository.save(c));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> get(@PathVariable Long id) {
        return customerRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
