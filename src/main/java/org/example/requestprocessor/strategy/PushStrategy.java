package org.example.requestprocessor.strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.requestprocessor.model.NotificationOutbox;
import org.example.requestprocessor.repository.NotificationOutboxRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PushStrategy implements NotificationStrategy {

    public final NotificationOutboxRepository notificationRepository;

    @Override
    public void process(String message){

        String key = UUID.randomUUID().toString();
        String body = """
                {"message": "%s"}
                """.formatted(message);
        NotificationOutbox pushOutbox = new NotificationOutbox();
        pushOutbox.setId(UUID.randomUUID());
        pushOutbox.setCreatedAt(LocalDateTime.now());
        pushOutbox.setTopic("push-events");
        pushOutbox.setKey(key);
        pushOutbox.setValue(message);
        pushOutbox.setSent(false);
        pushOutbox.setAttempt(1);

        notificationRepository.save(pushOutbox);
        log.info("Подготовлено сообщение для отправки. Key: {}, Payload: {}, topic: {}", key, body, pushOutbox.getTopic());
    }
}
