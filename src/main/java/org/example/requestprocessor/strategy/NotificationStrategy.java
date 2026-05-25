package org.example.requestprocessor.strategy;

import org.example.requestprocessor.model.enums.NotificationType;

public interface NotificationStrategy {

    void process(String message);

    NotificationType getType();
}
