package com.arka_EP.infrastructure.persistence.adapter;

import com.arka_EP.domain.model.Payment;
import com.arka_EP.domain.repository.PaymentRepository;
import com.arka_EP.infrastructure.persistence.jpa.PaymentEntity;
import com.arka_EP.infrastructure.persistence.jpa.SpringDataPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentRepositoryAdapter implements PaymentRepository {
    private final SpringDataPaymentRepository springRepo;

    @Override
    public Payment save(Payment p) {
        PaymentEntity e = PaymentEntity.builder()
                .id(p.getId())
                .orderId(p.getOrderId())
                .amount(p.getAmount())
                .method(p.getMethod())
                .status(p.getStatus())
                .createdAt(p.getCreatedAt())
                .build();
        var saved = springRepo.save(e);
        return new Payment(saved.getId(), saved.getOrderId(), saved.getAmount(), saved.getMethod(), saved.getStatus(), saved.getCreatedAt());
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return springRepo.findById(id).map(e -> new Payment(e.getId(), e.getOrderId(), e.getAmount(), e.getMethod(), e.getStatus(), e.getCreatedAt()));
    }
}
