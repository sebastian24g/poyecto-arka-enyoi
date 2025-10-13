package com.arka_EP.application.service;

import com.arka_EP.application.dto.PlaceOrderCommand;
import com.arka_EP.domain.model.Order;
import com.arka_EP.domain.model.OrderItem;
import com.arka_EP.domain.repository.OrderRepository;
import com.arka_EP.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderAppService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Long placeOrder(PlaceOrderCommand cmd) {
        var items = cmd.getItems().stream()
            .map(i -> new OrderItem(null, i.getProductId(), i.getQuantity(), i.getUnitPrice()))
            .collect(Collectors.toList());

        // decrease stock per product
        for(var it: items) {
            productRepository.decreaseStock(it.getProductId(), it.getQuantity());
        }

        Order o = Order.builder()
                .customerId(cmd.getCustomerId())
                .createdAt(LocalDateTime.now())
                .status("PENDING")
                .items(items)
                .total(cmd.getItems().stream().map(i -> i.getUnitPrice().multiply(new java.math.BigDecimal(i.getQuantity()))).reduce(new java.math.BigDecimal("0"), java.math.BigDecimal::add))
                .build();

        var saved = orderRepository.save(o);
        return saved.getId();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }
}
