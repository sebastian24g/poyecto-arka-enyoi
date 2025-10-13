package com.arka_EP.ui.rest.controller;

import com.arka_EP.application.dto.PlaceOrderCommand;
import com.arka_EP.application.service.OrderAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderAppService orderService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Long place(@RequestBody PlaceOrderCommand cmd) {
        return orderService.placeOrder(cmd);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Object get(@PathVariable Long id) {
        return orderService.findById(id).orElse(null);
    }
}
