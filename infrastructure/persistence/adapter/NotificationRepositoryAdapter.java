package com.arka_EP.infrastructure.persistence.adapter;

import com.arka_EP.domain.model.Notification;
import com.arka_EP.domain.repository.NotificationRepository;
import com.arka_EP.infrastructure.persistence.jpa.NotificationEntity;
import com.arka_EP.infrastructure.persistence.jpa.SpringDataNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NotificationRepositoryAdapter implements NotificationRepository {
    private final SpringDataNotificationRepository springRepo;

    @Override
    public Notification save(Notification n) {
        NotificationEntity e = NotificationEntity.builder()
                .id(n.getId())
                .customerId(n.getCustomerId())
                .message(n.getMessage())
                .type(n.getType())
                .sentAt(n.getSentAt())
                .read(n.getRead())
                .build();
        var saved = springRepo.save(e);
        return new Notification(saved.getId(), saved.getCustomerId(), saved.getMessage(), saved.getType(), saved.getSentAt(), saved.getRead());
    }

    @Override
    public List<Notification> findByCustomerId(Long customerId) {
        return springRepo.findByCustomerId(customerId).stream()
            .map(e -> new Notification(e.getId(), e.getCustomerId(), e.getMessage(), e.getType(), e.getSentAt(), e.getRead()))
            .collect(Collectors.toList());
    }
}
