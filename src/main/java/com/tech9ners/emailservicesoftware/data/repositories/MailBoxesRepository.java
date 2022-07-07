package com.tech9ners.emailservicesoftware.data.repositories;

import com.tech9ners.emailservicesoftware.data.models.MailBoxes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailBoxesRepository extends MongoRepository<MailBoxes,String> {
    MailBoxes findUserMailBoxByUserID(String emailAddress);
    MailBoxes existsByUserID(String emailAddress);
}
