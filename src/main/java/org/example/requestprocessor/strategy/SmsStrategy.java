package org.example.requestprocessor.strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.requestprocessor.model.NotificationOutbox;
import org.example.requestprocessor.model.enums.NotificationType;
import org.example.requestprocessor.repository.NotificationOutboxRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmsStrategy implements NotificationStrategy {

    private final NotificationOutboxRepository notificationRepository;

    @Override
    public NotificationType getType() {
        return NotificationType.SMS;
    }

    @Override
    public void process(String message){

        String key = UUID.randomUUID().toString();
        String body = """
                {"message": "%s"}
                """.formatted(message);

        NotificationOutbox smsOutbox = new NotificationOutbox();
        smsOutbox.setId(UUID.randomUUID());
        smsOutbox.setCreatedAt(LocalDateTime.now());
        smsOutbox.setTopic("sms-events");
        smsOutbox.setKey(key);
        smsOutbox.setValue(message);
        smsOutbox.setSent(false);
        smsOutbox.setAttempt(1);

        notificationRepository.save(smsOutbox);
        log.info("Подготовлено сообщение для отправки. Key: {}, Payload: {}, topic: {}", key, body, smsOutbox.getTopic());
    }
}
