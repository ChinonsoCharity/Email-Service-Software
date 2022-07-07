package com.tech9ners.emailservicesoftware.Dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteRequest {
    private String emailAddress;
    private String password;
    private String consent;
}
