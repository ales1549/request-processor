package org.example.requestprocessor.repository;

import org.example.requestprocessor.model.NotificationOutbox;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationOutboxRepository extends JpaRepository<NotificationOutbox, UUID> {
    List<NotificationOutbox> findBySentFalseOrderByCreatedAtAsc(Pageable pageable);
}
