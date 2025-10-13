package com.arka_EP.ui.rest.controller;

import com.arka_EP.domain.model.Notification;
import com.arka_EP.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationRepository notificationRepository;

    @PostMapping
    public ResponseEntity<Notification> create(@RequestBody Notification n) {
        return ResponseEntity.ok(notificationRepository.save(n));
    }

    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<List<Notification>> byCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(notificationRepository.findByCustomerId(customerId));
    }
}
