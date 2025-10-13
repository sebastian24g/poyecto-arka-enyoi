package com.arka_EP.infrastructure.persistence.adapter;

import com.arka_EP.domain.model.SystemConfig;
import com.arka_EP.domain.repository.SystemConfigRepository;
import com.arka_EP.infrastructure.persistence.jpa.SystemConfigEntity;
import com.arka_EP.infrastructure.persistence.jpa.SpringDataSystemConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SystemConfigRepositoryAdapter implements SystemConfigRepository {
    private final SpringDataSystemConfigRepository springRepo;

    @Override
    public Optional<SystemConfig> findByKey(String key) {
        return springRepo.findById(key).map(e -> new SystemConfig(e.getKey(), e.getValue()));
    }

    @Override
    public SystemConfig save(SystemConfig cfg) {
        SystemConfigEntity e = SystemConfigEntity.builder().key(cfg.getKey()).value(cfg.getValue()).build();
        var saved = springRepo.save(e);
        return new SystemConfig(saved.getKey(), saved.getValue());
    }
}
