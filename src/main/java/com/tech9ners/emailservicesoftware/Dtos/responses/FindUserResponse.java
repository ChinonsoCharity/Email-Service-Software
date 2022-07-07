package com.tech9ners.emailservicesoftware.Dtos.responses;

import com.tech9ners.emailservicesoftware.data.models.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindUserResponse {
    private String findEmailAddress;
    private String fullName;
    private List<Notification> findNotifications = new ArrayList<>();

}
