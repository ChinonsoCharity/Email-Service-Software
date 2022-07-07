package com.tech9ners.emailservicesoftware.data.repositories;

import com.tech9ners.emailservicesoftware.data.models.MailBox;
import com.tech9ners.emailservicesoftware.data.models.MailType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailBoxRepository extends MongoRepository<MailBox, String> {
    MailBox findByUserID (String userID);
    boolean existsByUserID(String userID);
    MailBox findMailBoxByMailNameAndAndUsername(String username,MailType mailType);
}
