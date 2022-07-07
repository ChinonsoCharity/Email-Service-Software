package com.tech9ners.emailservicesoftware.data.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("MailBox")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MailBox {
    @Id
    private String userID;
    private String username;
    private MailType mailName;
    private List<Message> messages = new ArrayList<>();


    public MailBox(String username, MailType type) {
        this.username = username;
        this.mailName = type;
    }

}
