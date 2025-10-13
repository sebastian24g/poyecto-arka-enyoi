package com.arka_EP.ui.rest.controller;

import com.arka_EP.domain.model.User;
import com.arka_EP.domain.model.Role;
import com.arka_EP.application.service.UserAppService;
import com.arka_EP.domain.repository.UserRepository;
import com.arka_EP.application.dto.AssignRolesRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Set;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserAppService userAppService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> create(@RequestBody User u) {
        return ResponseEntity.ok(userRepository.save(u));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<User> get(@PathVariable Long id) {
        return userRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Set<Role>> getRoles(@PathVariable Long id) {
        return ResponseEntity.ok(userAppService.getRoles(id));
    }

    @PostMapping("/{id}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> assignRoles(@PathVariable Long id, @Valid @RequestBody AssignRolesRequest req) {
        userAppService.assignRoles(id, req.getRoleIds());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/roles/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> removeRole(@PathVariable Long id, @PathVariable Long roleId) {
        userAppService.removeRole(id, roleId);
        return ResponseEntity.noContent().build();
    }
}
