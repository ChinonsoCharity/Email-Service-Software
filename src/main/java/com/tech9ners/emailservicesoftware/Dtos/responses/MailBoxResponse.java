package com.tech9ners.emailservicesoftware.Dtos.responses;

import com.tech9ners.emailservicesoftware.data.models.MailBox;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailBoxResponse {
    private MailBox userMailbox;
}
