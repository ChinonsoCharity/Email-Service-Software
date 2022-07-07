package com.tech9ners.emailservicesoftware.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document("Notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    private String notificationID;
    private String notificationMessage;
    private String subject;
    private String senderEmail;
    private List<String> receiverEmail = new ArrayList<>();
    private LocalDateTime creationTime = LocalDateTime.now();

}


