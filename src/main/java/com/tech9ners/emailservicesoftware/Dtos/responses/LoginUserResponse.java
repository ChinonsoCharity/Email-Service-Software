package com.tech9ners.emailservicesoftware.Dtos.responses;

import com.tech9ners.emailservicesoftware.data.models.Notification;
import com.tech9ners.emailservicesoftware.data.models.MailBoxes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserResponse {
    private String loginMessage;
    private List<Notification> notifications;
    private MailBoxes userMailBox;
}
