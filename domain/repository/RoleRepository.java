package com.arka_EP.domain.repository;

import com.arka_EP.domain.model.Role;
import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findById(Long id);
    Optional<Role> findByName(String name);
    Role save(Role r);
}
