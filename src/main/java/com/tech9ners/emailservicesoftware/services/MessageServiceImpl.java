package com.tech9ners.emailservicesoftware.services;

import com.tech9ners.emailservicesoftware.Dtos.requests.MessageRequest;
import com.tech9ners.emailservicesoftware.data.models.Message;
import com.tech9ners.emailservicesoftware.data.repositories.MessageRepository;
import com.tech9ners.emailservicesoftware.exceptions.UserAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    private int count = 1;

    @Override
    public Message createMessage(MessageRequest messageRequest) {
        Message newMessage = new Message();
        newMessage.setMessageID(String.valueOf(count));
        newMessage.setSenderEmail(messageRequest.getSenderEmail());
        newMessage.setReceiverEmail(messageRequest.getReceiverEmail());
        newMessage.setSubject(messageRequest.getSubject());
        newMessage.setMessageBody(messageRequest.getMessageBody());
        newMessage.setCreationTime(LocalDateTime.now());

        Message theMessage = messageRepository.save(newMessage);
        count++;

        return theMessage;
    }

    @Override
    public Message findMessage(String messageID) throws UserAccountException {
        Message theMessage = messageRepository.findByMessageID(messageID);
        if (theMessage == null) throw new UserAccountException("Message not found");
        return theMessage;
    }

    @Override
    public void markMessageAsDelivered(String messageID) throws UserAccountException {
        Message theMessage = messageRepository.findByMessageID(messageID);
        if (messageRepository.existsByMessageID(messageID)) {
            theMessage.setDelivered(true);
            messageRepository.save(theMessage);
        } else throw new UserAccountException("Message not found");
    }


    @Override
    public void markMessageAsRead(String messageID) throws UserAccountException {
        Message theMessage = messageRepository.findByMessageID(messageID);
        if (messageRepository.existsByMessageID(messageID)) {
            theMessage.setDelivered(true);
            theMessage.setRead(true);
            messageRepository.save(theMessage);
        } else throw new UserAccountException("Message not found");
    }


    @Override
    public void deleteMessage(String id) throws UserAccountException {
        Message selectedMessage = messageRepository.findByMessageID(id);
        if (!(messageRepository.existsByMessageID(id))) throw new UserAccountException("Message not found");
        messageRepository.delete(selectedMessage);

        return;
    }

    @Override
    public List<Message> findMessageByAnyWord(String word) throws UserAccountException {
        List<Message> theFoundMessage = findMessageByString(word);
        if (theFoundMessage == null)throw new UserAccountException("Message does not exist");
        return theFoundMessage;
    }

    private List<Message> findMessageByContent(String content) {
        List<Message> foundMessages = new ArrayList<>();
        List<Message> theMessage = messageRepository.findAll();
        for (Message message : theMessage) {
            if (message.getMessageBody().toLowerCase().contains(content.toLowerCase()))
                   foundMessages.add(message);
               }

        return foundMessages;

    }
    private List<Message> findMessageByString(String word) {
        List<Message> allFoundMessages = new ArrayList<>();
        allFoundMessages.addAll(findMessageByContent(word));
        allFoundMessages.addAll(messageRepository.findBySenderEmail(word));
        allFoundMessages.addAll(messageRepository.findByReceiverEmail(word));
        allFoundMessages.addAll(messageRepository.findBySubject(word));
        return allFoundMessages;
    }
}