package com.arka_EP.infrastructure.persistence.adapter;

import com.arka_EP.domain.model.User;
import com.arka_EP.domain.model.Role;
import com.arka_EP.domain.repository.UserRepository;
import com.arka_EP.infrastructure.persistence.jpa.UserEntity;
import com.arka_EP.infrastructure.persistence.jpa.RoleEntity;
import com.arka_EP.infrastructure.persistence.jpa.SpringDataUserRepository;
import com.arka_EP.infrastructure.persistence.jpa.SpringDataRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {
    private final SpringDataUserRepository userRepo;
    private final SpringDataRoleRepository roleRepo;

    @Override
    public Optional<User> findById(Long id) {
        return userRepo.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username).map(this::toDomain);
    }

    @Override
    public User save(User u) {
        UserEntity e = UserEntity.builder()
                .id(u.getId())
                .username(u.getUsername())
                .password(u.getPassword())
                .email(u.getEmail())
                .build();
        // roles handled separately if present
        var saved = userRepo.save(e);
        // if roles provided in domain, attach them
        if (u.getRoles() != null && !u.getRoles().isEmpty()) {
            var userEntity = userRepo.findById(saved.getId()).orElse(saved);
            var roleEntities = u.getRoles().stream().map(r -> roleRepo.findById(r.getId()).orElse(null)).filter(x->x!=null).collect(Collectors.toSet());
            userEntity.setRoles(roleEntities);
            userRepo.save(userEntity);
            saved = userEntity;
        }
        return toDomain(saved);
    }

    @Override
    public Set<Role> findRolesByUserId(Long userId) {
        return userRepo.findById(userId).map(u -> {
            if (u.getRoles() == null) return Set.of();
            return u.getRoles().stream().map(r -> new Role(r.getId(), r.getName())).collect(Collectors.toSet());
        }).orElse(Set.of());
    }

    @Override
    @Transactional
    public void assignRoles(Long userId, List<Long> roleIds) {
        var userOpt = userRepo.findById(userId);
        if (userOpt.isEmpty()) throw new IllegalArgumentException("User not found");
        var user = userOpt.get();
        var roles = roleIds.stream()
                .map(id -> roleRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Role not found: " + id)))
                .collect(Collectors.toSet());
        if (user.getRoles() != null) {
            user.getRoles().addAll(roles);
        } else {
            user.setRoles(roles);
        }
        userRepo.save(user);
    }

    @Override
    @Transactional
    public void removeRole(Long userId, Long roleId) {
        var userOpt = userRepo.findById(userId);
        if (userOpt.isEmpty()) throw new IllegalArgumentException("User not found");
        var user = userOpt.get();
        if (user.getRoles() != null) {
            user.getRoles().removeIf(r -> r.getId().equals(roleId));
            userRepo.save(user);
        }
    }

    private User toDomain(UserEntity e) {
        return User.builder()
                .id(e.getId())
                .username(e.getUsername())
                .password(e.getPassword())
                .email(e.getEmail())
                .roles(e.getRoles() == null ? null : e.getRoles().stream().map(re -> new Role(re.getId(), re.getName())).collect(Collectors.toSet()))
                .build();
    }
}
