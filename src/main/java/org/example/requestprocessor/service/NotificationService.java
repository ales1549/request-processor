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


    public void process(NotificationRequest request){
         if(strategy == null){
             throw new IllegalArgumentException("Неизвестный тип уведомления: " + request.getType());
         }
         strategy.process(request.getMessage());
    }
}
