package org.example.requestprocessor.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.requestprocessor.config.OutboxProperties;
import org.example.requestprocessor.model.NotificationOutbox;
import org.example.requestprocessor.repository.NotificationOutboxRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxScheduler {

    private final NotificationOutboxRepository notificationRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final OutboxProperties outboxProperties;

    @Scheduled(fixedDelayString = "${outbox.delay-ms}")
    public void processOut(){
        List<NotificationOutbox> messages = notificationRepository.findBySentFalseOrderByCreatedAtAsc(
                PageRequest.of(0, outboxProperties.getBatchSize()));
        for (NotificationOutbox message : messages){
            try {
                kafkaTemplate.send(message.getTopic(), message.getKey(), message.getValue()).get();
                message.setSent(true);
                notificationRepository.save(message);
            }catch (Exception e){
                log.error("Ошибка отправки сообщения key={}: {}", message.getKey(), e.getMessage());
                message.setAttempt(message.getAttempt() + 1);
                notificationRepository.save(message);
            }
        }
    }
}
