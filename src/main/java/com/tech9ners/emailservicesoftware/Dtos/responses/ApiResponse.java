package com.tech9ners.emailservicesoftware.Dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ApiResponse {
    private Object payload;
    private String message;
    private boolean isSuccessful;
    private int statusCode;
}