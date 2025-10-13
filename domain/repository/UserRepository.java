package com.arka_EP.domain.repository;

import com.arka_EP.domain.model.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    User save(User u);
}
