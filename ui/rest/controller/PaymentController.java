package com.arka_EP.ui.rest.controller;

import com.arka_EP.domain.model.Payment;
import com.arka_EP.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentRepository paymentRepository;

    @PostMapping
    public ResponseEntity<Payment> create(@RequestBody Payment p) {
        return ResponseEntity.ok(paymentRepository.save(p));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> get(@PathVariable Long id) {
        return paymentRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
