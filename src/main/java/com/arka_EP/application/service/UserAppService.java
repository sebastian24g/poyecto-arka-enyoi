package com.arka_EP.application.service;

import com.arka_EP.domain.model.User;
import com.arka_EP.domain.model.Role;
import com.arka_EP.domain.repository.UserRepository;
import com.arka_EP.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserAppService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public User get(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public Set<Role> getRoles(Long userId) {
        return userRepository.findRolesByUserId(userId);
    }

    public void assignRoles(Long userId, List<Long> roleIds) {
        // validate roles exist
        for (Long rid : roleIds) {
            roleRepository.findById(rid).orElseThrow(() -> new IllegalArgumentException("Role not found: " + rid));
        }
        userRepository.assignRoles(userId, roleIds);
    }

    public void removeRole(Long userId, Long roleId) {
        roleRepository.findById(roleId).orElseThrow(() -> new IllegalArgumentException("Role not found"));
        userRepository.removeRole(userId, roleId);
    }
}
