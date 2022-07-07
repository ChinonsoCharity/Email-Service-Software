package com.tech9ners.emailservicesoftware.data.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document("Mails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String messageID;
    @Email
    private String senderEmail;
    @Email
    private List<String> receiverEmail = new ArrayList<>();
    private String subject;
    private String messageBody;
    private boolean isRead;
    private boolean isDelivered;
    private LocalDateTime creationTime = LocalDateTime.now();
}
