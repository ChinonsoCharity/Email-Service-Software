package com.tech9ners.emailservicesoftware.Dtos.requests;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {
    @Size(max = 20)
    private String lastName;

    @Size(max = 20)
    private String firstName;

    @Id
    @Email
    private String emailAddress;

    @Pattern(regexp = "^((?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9])){4,12}$",
            message = "password must contain at least 1 uppercase, 1 lowercase, 1 special character and 1 digit ")
    private String password;



}
