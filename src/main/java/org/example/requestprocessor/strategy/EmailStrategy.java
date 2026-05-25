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
public class EmailStrategy implements NotificationStrategy {

    private final NotificationOutboxRepository notificationRepository;

    @Override
    public NotificationType getType() {
        return NotificationType.EMAIL;
    }


    @Override
    public void process(String message){

        String key = UUID.randomUUID().toString();
        String body = """
                {"message": "%s"}
                """.formatted(message);

        NotificationOutbox emailOutbox = new NotificationOutbox();
        emailOutbox.setId(UUID.randomUUID());
        emailOutbox.setCreatedAt(LocalDateTime.now());
        emailOutbox.setTopic("email-events");
        emailOutbox.setKey(key);
        emailOutbox.setValue(message);
        emailOutbox.setSent(false);
        emailOutbox.setAttempt(1);

        notificationRepository.save(emailOutbox);
        log.info("Подготовлено сообщение для отправки. Key: {}, Payload: {}, topic: {}", key, body, emailOutbox.getTopic());
    }

}
