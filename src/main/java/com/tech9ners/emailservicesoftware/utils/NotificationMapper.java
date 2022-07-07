package com.tech9ners.emailservicesoftware.utils;

import com.tech9ners.emailservicesoftware.data.models.Message;
import com.tech9ners.emailservicesoftware.data.models.Notification;
import com.tech9ners.emailservicesoftware.data.repositories.NotificationRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class NotificationMapper {
    @Autowired
    private NotificationRepository notificationRepository;


    public  Notification receiverNoticeMapper(Message newMessage) {
         int count = 1;
        Notification receiverNotice = new Notification();
        receiverNotice.setNotificationID(String.valueOf(count));
        receiverNotice.setNotificationMessage("You have one new mail");
        receiverNotice.setSubject(newMessage.getSubject());
        receiverNotice.setSenderEmail(newMessage.getSenderEmail());
        receiverNotice.setReceiverEmail(newMessage.getReceiverEmail());
        receiverNotice.setCreationTime(LocalDateTime.now());

        notificationRepository.save(receiverNotice);
        count++;

        return receiverNotice;
    }
    public Notification senderNoticeMapper(Message newMessage){
        int count = 1;
        Notification senderNotice = new Notification();
        senderNotice.setNotificationID(String.valueOf(count));
        senderNotice.setNotificationMessage("Mail sent");
        senderNotice.setSubject(newMessage.getSubject());
        senderNotice.setSenderEmail(newMessage.getSenderEmail());
        senderNotice.setReceiverEmail(newMessage.getReceiverEmail());
        senderNotice.setCreationTime(LocalDateTime.now());
        notificationRepository.save(senderNotice);
        count++;
        return senderNotice;
    }



}
