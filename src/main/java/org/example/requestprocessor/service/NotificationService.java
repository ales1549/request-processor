package org.example.requestprocessor.service;

import lombok.RequiredArgsConstructor;
import org.example.requestprocessor.model.dto.NotificationRequest;
import org.example.requestprocessor.model.enums.NotificationType;
import org.example.requestprocessor.strategy.NotificationStrategy;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final Map<String, NotificationStrategy> strategies;

    public void process(NotificationRequest request){
        String strategyName = resolveStrategyName(request.getType());
        NotificationStrategy strategy = strategies.get(strategyName);
         if(strategy == null){
             throw new IllegalArgumentException("Неизвестный тип уведомления: " + request.getType());
         }
         strategy.process(request.getMessage());
    }

    public String resolveStrategyName(NotificationType type){
        return switch(type){
            case SMS -> "smsStrategy";
            case EMAIL -> "emailStrategy";
            case PUSH -> "pushStrategy";
            case TG_MESSAGE -> "telegramStrategy";
        };
    }
}
