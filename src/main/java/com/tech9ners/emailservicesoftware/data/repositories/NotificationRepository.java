package com.tech9ners.emailservicesoftware.data.repositories;

import com.tech9ners.emailservicesoftware.data.models.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification,String> {
}
