package com.tech9ners.emailservicesoftware.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Document("MailBoxes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailBoxes {
    @Id
    private String userID;

   private List<MailBox> messageBoxes = new ArrayList<>();

}
