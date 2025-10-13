package com.arka_EP.ui.rest.controller;

import com.arka_EP.domain.model.Cart;
import com.arka_EP.domain.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartRepository cartRepository;

    @PostMapping
    public ResponseEntity<Cart> create(@RequestBody Cart c) {
        return ResponseEntity.ok(cartRepository.save(c));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> get(@PathVariable Long id) {
        return cartRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
