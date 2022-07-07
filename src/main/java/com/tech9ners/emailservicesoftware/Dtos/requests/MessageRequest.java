package com.tech9ners.emailservicesoftware.Dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {
    @Email
    private String senderEmail;
    @Email
    private List<String> receiverEmail = new ArrayList<>();;
    private String subject;
    private String messageBody;

}
