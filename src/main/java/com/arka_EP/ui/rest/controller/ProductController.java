package com.arka_EP.ui.rest.controller;

import com.arka_EP.application.service.ProductAppService;
import com.arka_EP.application.dto.ProductDto;
import com.arka_EP.application.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductAppService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductDto dto) {
        var created = productService.create(ProductMapper.toDomain(dto));
        return ResponseEntity.ok(ProductMapper.toDto(created));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ProductDto> get(@PathVariable Long id) {
        var p = productService.get(id);
        return ResponseEntity.ok(ProductMapper.toDto(p));
    }

    @GetMapping("/low-stock")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<ProductDto>> lowStock(@RequestParam(defaultValue = "5") int threshold) {
        var list = productService.lowStock(threshold).stream().map(ProductMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }
}
