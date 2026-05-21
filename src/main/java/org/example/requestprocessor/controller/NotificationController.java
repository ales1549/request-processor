package org.example.requestprocessor.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.requestprocessor.model.dto.NotificationRequest;
import org.example.requestprocessor.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Void> sendNotification(@Valid @RequestBody NotificationRequest notification) {
        notificationService.process(notification);
        return ResponseEntity.ok().build();
    }
}
