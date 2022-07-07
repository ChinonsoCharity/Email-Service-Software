package com.tech9ners.emailservicesoftware.Dtos.requests;

import com.tech9ners.emailservicesoftware.data.models.MailType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ViewBoxRequest {
    private String emailAddress;
    private String password;
    private String mailType;
}
