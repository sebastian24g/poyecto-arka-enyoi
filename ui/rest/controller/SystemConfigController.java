package com.arka_EP.ui.rest.controller;

import com.arka_EP.domain.model.SystemConfig;
import com.arka_EP.domain.repository.SystemConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config")
@RequiredArgsConstructor
public class SystemConfigController {
    private final SystemConfigRepository cfgRepo;

    @GetMapping("/{key}")
    public ResponseEntity<SystemConfig> get(@PathVariable String key) {
        return cfgRepo.findByKey(key).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SystemConfig> save(@RequestBody SystemConfig cfg) {
        return ResponseEntity.ok(cfgRepo.save(cfg));
    }
}
