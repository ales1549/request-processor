package org.example.requestprocessor.service;

import lombok.RequiredArgsConstructor;
import org.example.requestprocessor.model.dto.NotificationRequest;
import org.example.requestprocessor.model.enums.NotificationType;
import org.example.requestprocessor.strategy.NotificationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final Map<NotificationType, NotificationStrategy> strategies;

    @Autowired
    public NotificationService(List<NotificationStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(NotificationStrategy::getType, strategy -> strategy));
    }

    public void process(NotificationRequest request){
        NotificationStrategy strategy = strategies.get(request.getType());
         if(strategy == null){
             throw new IllegalArgumentException("Неизвестный тип уведомления: " + request.getType());
         }
         strategy.process(request.getMessage());
    }

}
