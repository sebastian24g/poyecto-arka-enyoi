package com.arka_EP.infrastructure.persistence.adapter;

import com.arka_EP.domain.model.User;
import com.arka_EP.domain.repository.UserRepository;
import com.arka_EP.infrastructure.persistence.jpa.UserEntity;
import com.arka_EP.infrastructure.persistence.jpa.SpringDataUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {
    private final SpringDataUserRepository userRepo;

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
        var saved = userRepo.save(e);
        return toDomain(saved);
    }

    private User toDomain(UserEntity e) {
        return User.builder()
                .id(e.getId())
                .username(e.getUsername())
                .password(e.getPassword())
                .email(e.getEmail())
                .build();
    }
}
