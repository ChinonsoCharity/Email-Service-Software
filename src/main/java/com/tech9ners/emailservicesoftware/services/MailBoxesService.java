package com.tech9ners.emailservicesoftware.services;

import com.tech9ners.emailservicesoftware.Dtos.requests.ForwardMessageRequest;
import com.tech9ners.emailservicesoftware.Dtos.requests.MessageRequest;
import com.tech9ners.emailservicesoftware.Dtos.requests.ViewBoxRequest;
import com.tech9ners.emailservicesoftware.Dtos.responses.SentMessageResponse;
import com.tech9ners.emailservicesoftware.data.models.MailBox;
import com.tech9ners.emailservicesoftware.data.models.MailBoxes;
import com.tech9ners.emailservicesoftware.exceptions.UserAccountException;

public interface MailBoxesService {
    MailBoxes createUserMailBox(String userID, MailBox sent, MailBox inbox);
    SentMessageResponse sentMessage(MessageRequest messageRequest) throws UserAccountException;
    MailBox viewMailBox(ViewBoxRequest viewRequest) throws UserAccountException;
    void deleteMailBoxes(String emailAddress) throws UserAccountException;
    SentMessageResponse forwardMessageToReceiver(ForwardMessageRequest forwardRequest) throws UserAccountException;


}
