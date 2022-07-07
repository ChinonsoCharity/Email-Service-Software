package com.tech9ners.emailservicesoftware.Dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForwardMessageRequest {
    private String senderEmail;
    private List<String> receiversEmail;
    private String messageID;
}
