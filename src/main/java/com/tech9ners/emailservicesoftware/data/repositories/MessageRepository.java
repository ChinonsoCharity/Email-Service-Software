package com.tech9ners.emailservicesoftware.data.repositories;

import com.tech9ners.emailservicesoftware.data.models.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message,String> {
    Message findByMessageID(String id);
    boolean existsByMessageID(String id);
    List<Message> findBySenderEmail(String email);
    List<Message> findByReceiverEmail(String email);
    List<Message> findBySubject(String subject);
}

