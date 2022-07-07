package com.tech9ners.emailservicesoftware.services;

import com.tech9ners.emailservicesoftware.Dtos.requests.MessageRequest;
import com.tech9ners.emailservicesoftware.data.models.Message;
import com.tech9ners.emailservicesoftware.exceptions.UserAccountException;

import java.util.List;

public interface MessageService {
Message createMessage(MessageRequest messageRequest);
Message findMessage(String messageID) throws UserAccountException;
void markMessageAsRead(String messageID) throws UserAccountException;
void markMessageAsDelivered(String messageID) throws UserAccountException;
void deleteMessage(String id) throws UserAccountException;
List<Message> findMessageByAnyWord(String word) throws UserAccountException;
}

