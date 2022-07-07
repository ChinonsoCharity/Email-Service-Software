package com.tech9ners.emailservicesoftware.Dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SentMessageResponse {
    private String notificationMessage;
    private LocalDateTime creationTime = LocalDateTime.now();
}
