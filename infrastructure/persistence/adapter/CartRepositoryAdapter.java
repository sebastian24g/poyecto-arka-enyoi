package com.arka_EP.infrastructure.persistence.adapter;

import com.arka_EP.domain.model.Cart;
import com.arka_EP.domain.model.CartItem;
import com.arka_EP.domain.repository.CartRepository;
import com.arka_EP.infrastructure.persistence.jpa.CartEntity;
import com.arka_EP.infrastructure.persistence.jpa.CartItemEntity;
import com.arka_EP.infrastructure.persistence.jpa.SpringDataCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartRepositoryAdapter implements CartRepository {
    private final SpringDataCartRepository springRepo;

    @Override
    public Cart save(Cart c) {
        CartEntity e = CartEntity.builder()
                .id(c.getId())
                .customerId(c.getCustomerId())
                .createdAt(c.getCreatedAt())
                .status(c.getStatus())
                .items(c.getItems().stream().map(i -> CartItemEntity.builder()
                    .productId(i.getProductId())
                    .quantity(i.getQuantity())
                    .unitPrice(i.getUnitPrice())
                    .build()).collect(Collectors.toList()))
                .build();
        var saved = springRepo.save(e);
        return Cart.builder()
                .id(saved.getId())
                .customerId(saved.getCustomerId())
                .createdAt(saved.getCreatedAt())
                .status(saved.getStatus())
                .items(saved.getItems().stream().map(it -> new CartItem(it.getId(), it.getProductId(), it.getQuantity(), it.getUnitPrice())).collect(Collectors.toList()))
                .build();
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return springRepo.findById(id).map(e -> Cart.builder()
                .id(e.getId())
                .customerId(e.getCustomerId())
                .createdAt(e.getCreatedAt())
                .status(e.getStatus())
                .items(e.getItems().stream().map(it -> new CartItem(it.getId(), it.getProductId(), it.getQuantity(), it.getUnitPrice())).collect(Collectors.toList()))
                .build());
    }
}
