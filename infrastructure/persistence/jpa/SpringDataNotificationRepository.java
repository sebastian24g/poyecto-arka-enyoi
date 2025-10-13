package com.arka_EP.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SpringDataNotificationRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findByCustomerId(Long customerId);
}
