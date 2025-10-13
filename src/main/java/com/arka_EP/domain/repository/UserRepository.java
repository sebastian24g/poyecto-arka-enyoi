package com.arka_EP.domain.repository;

import com.arka_EP.domain.model.User;
import com.arka_EP.domain.model.Role;
import java.util.Optional;
import java.util.Set;
import java.util.List;

public interface UserRepository {
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    User save(User u);

    // role management
    Set<Role> findRolesByUserId(Long userId);
    void assignRoles(Long userId, List<Long> roleIds);
    void removeRole(Long userId, Long roleId);
}
