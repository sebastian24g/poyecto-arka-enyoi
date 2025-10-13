package com.arka_EP.domain.repository;

import com.arka_EP.domain.model.SystemConfig;
import java.util.Optional;

public interface SystemConfigRepository {
    Optional<SystemConfig> findByKey(String key);
    SystemConfig save(SystemConfig cfg);
}
