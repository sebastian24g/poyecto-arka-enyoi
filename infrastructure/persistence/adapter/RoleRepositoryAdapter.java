package com.arka_EP.infrastructure.persistence.adapter;

import com.arka_EP.domain.model.Role;
import com.arka_EP.domain.repository.RoleRepository;
import com.arka_EP.infrastructure.persistence.jpa.RoleEntity;
import com.arka_EP.infrastructure.persistence.jpa.SpringDataRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoleRepositoryAdapter implements RoleRepository {
    private final SpringDataRoleRepository springRepo;

    @Override
    public Optional<Role> findById(Long id) {
        return springRepo.findById(id).map(e -> new Role(e.getId(), e.getName()));
    }

    @Override
    public Optional<Role> findByName(String name) {
        return springRepo.findByName(name).map(e -> new Role(e.getId(), e.getName()));
    }

    @Override
    public Role save(Role r) {
        RoleEntity e = RoleEntity.builder().id(r.getId()).name(r.getName()).build();
        var saved = springRepo.save(e);
        return new Role(saved.getId(), saved.getName());
    }
}
