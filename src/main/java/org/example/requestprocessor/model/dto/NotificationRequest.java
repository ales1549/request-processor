package org.example.requestprocessor.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.example.requestprocessor.model.enums.NotificationType;

@Getter
@Setter
public class NotificationRequest {

    @NotNull
    private NotificationType type;

    @NotBlank
    private String message;

}
