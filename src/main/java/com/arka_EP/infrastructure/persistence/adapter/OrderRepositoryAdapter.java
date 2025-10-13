package com.arka_EP.infrastructure.persistence.adapter;

import com.arka_EP.domain.model.Order;
import com.arka_EP.domain.model.OrderItem;
import com.arka_EP.domain.repository.OrderRepository;
import com.arka_EP.infrastructure.persistence.jpa.OrderEntity;
import com.arka_EP.infrastructure.persistence.jpa.OrderItemEntity;
import com.arka_EP.infrastructure.persistence.jpa.SpringDataOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {
    private final SpringDataOrderRepository springRepo;

    @Override
    public Order save(Order o) {
        OrderEntity e = OrderEntity.builder()
                .id(o.getId())
                .customerId(o.getCustomerId())
                .createdAt(o.getCreatedAt())
                .status(o.getStatus())
                .total(o.getTotal())
                .items(o.getItems().stream().map(i -> OrderItemEntity.builder()
                        .productId(i.getProductId())
                        .quantity(i.getQuantity())
                        .unitPrice(i.getUnitPrice())
                        .build()).collect(Collectors.toList()))
                .build();
        var saved = springRepo.save(e);
        return Order.builder()
                .id(saved.getId())
                .customerId(saved.getCustomerId())
                .createdAt(saved.getCreatedAt())
                .status(saved.getStatus())
                .total(saved.getTotal())
                .items(saved.getItems().stream().map(it -> new OrderItem(it.getId(), it.getProductId(), it.getQuantity(), it.getUnitPrice())).collect(Collectors.toList()))
                .build();
    }

    @Override
    public java.util.Optional<Order> findById(Long id) {
        return springRepo.findById(id).map(e -> Order.builder()
            .id(e.getId())
            .customerId(e.getCustomerId())
            .createdAt(e.getCreatedAt())
            .status(e.getStatus())
            .total(e.getTotal())
            .items(e.getItems().stream().map(it -> new OrderItem(it.getId(), it.getProductId(), it.getQuantity(), it.getUnitPrice())).collect(Collectors.toList()))
            .build());
    }
}
