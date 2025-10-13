package com.arka_EP.domain.repository;

import com.arka_EP.domain.model.Notification;
import java.util.List;

public interface NotificationRepository {
    Notification save(com.arka_EP.domain.model.Notification n);
    List<com.arka_EP.domain.model.Notification> findByCustomerId(Long customerId);
}
