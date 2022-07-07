package com.tech9ners.emailservicesoftware.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Document("Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Email
    private String emailAddress;

    @Valid
    private String password;

    @Size(max = 20)
    private String lastName;

    @Size(max = 20)
    private String firstName;

    private List<Notification> notifications = new ArrayList<>();

}
