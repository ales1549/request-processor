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
public class TelegramStrategy implements NotificationStrategy {

    private final NotificationOutboxRepository notificationRepository;

    @Override
    public void process(String message){

        String key = UUID.randomUUID().toString();
        String body = """
                {"message": "%s"}
                """.formatted(message);

        NotificationOutbox telegramOutbox = new NotificationOutbox();
        telegramOutbox.setId(UUID.randomUUID());
        telegramOutbox.setCreatedAt(LocalDateTime.now());
        telegramOutbox.setTopic("telegram-events");
        telegramOutbox.setKey(key);
        telegramOutbox.setValue(message);
        telegramOutbox.setSent(false);
        telegramOutbox.setAttempt(1);

        notificationRepository.save(telegramOutbox);
        log.info("Подготовлено сообщение для отправки. Key: {}, Payload: {}, topic: {}", key, body, telegramOutbox.getTopic());
    }
}

