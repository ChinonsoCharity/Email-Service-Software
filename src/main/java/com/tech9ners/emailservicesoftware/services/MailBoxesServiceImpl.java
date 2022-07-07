package com.tech9ners.emailservicesoftware.services;

import com.tech9ners.emailservicesoftware.Dtos.requests.ForwardMessageRequest;
import com.tech9ners.emailservicesoftware.Dtos.requests.MessageRequest;
import com.tech9ners.emailservicesoftware.Dtos.requests.ViewBoxRequest;
import com.tech9ners.emailservicesoftware.Dtos.responses.SentMessageResponse;
import com.tech9ners.emailservicesoftware.data.models.*;
import com.tech9ners.emailservicesoftware.data.repositories.MailBoxRepository;
import com.tech9ners.emailservicesoftware.data.repositories.MailBoxesRepository;
import com.tech9ners.emailservicesoftware.data.repositories.MessageRepository;
import com.tech9ners.emailservicesoftware.data.repositories.UserRepository;
import com.tech9ners.emailservicesoftware.exceptions.UserAccountException;
import com.tech9ners.emailservicesoftware.utils.NotificationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MailBoxesServiceImpl implements MailBoxesService {
    @Autowired
    private MailBoxesRepository mailBoxesRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    private MessageService messageService;
    @Autowired
    private MailBoxService mailBoxService;
    @Autowired
    private MailBoxRepository mailBoxRepository;
    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public MailBoxes createUserMailBox(String userID, MailBox sentBox, MailBox inbox) {
        MailBoxes newUserMailBox = new MailBoxes();

        newUserMailBox.setUserID(userID);
        newUserMailBox.getMessageBoxes().add(sentBox);
        newUserMailBox.getMessageBoxes().add(inbox);

        return mailBoxesRepository.save(newUserMailBox);
    }

    @Override
    public SentMessageResponse sentMessage(MessageRequest messageRequest) throws UserAccountException {
        User sender = userRepository.findUsersByEmailAddress(messageRequest.getSenderEmail());
        if (!(userRepository.existsByEmailAddress(messageRequest.getSenderEmail()))) {
            throw new UserAccountException("Account with this email does not exist");
        }

        List<User> receivers = new ArrayList<>();
        for (String receiverEmail : messageRequest.getReceiverEmail()) {
            User receiver = userRepository.findUsersByEmailAddress(receiverEmail);
            if (!(userRepository.existsByEmailAddress(receiverEmail))) {
                throw new UserAccountException("Account with this email does not exist");
            }
            receivers.add(receiver);
        }

        Message newMessage = messageService.createMessage(messageRequest);
        MailBoxes senderUserMailBox = mailBoxesRepository.findUserMailBoxByUserID(messageRequest.getSenderEmail());
        MailBox sentBox = senderUserMailBox.getMessageBoxes().get(0);
        sentBox.getMessages().add(newMessage);

        mailBoxRepository.save(sentBox);
        mailBoxesRepository.save(senderUserMailBox);

        List <MailBoxes> theReceiversMailBoxes = new ArrayList<>();
        for (User receiver : receivers){
            theReceiversMailBoxes.add(mailBoxesRepository.findUserMailBoxByUserID(receiver.getEmailAddress()));
        }
        List<MailBox> receiversInboxes = new ArrayList<>();
        for (MailBoxes receiverMailBoxes : theReceiversMailBoxes) {
            receiversInboxes.add(receiverMailBoxes.getMessageBoxes().get(1));
            for(MailBox inbox: receiversInboxes){
                inbox.getMessages().add(newMessage);
                mailBoxRepository.save(inbox);
        }
        mailBoxesRepository.saveAll(theReceiversMailBoxes);
    }
        Notification senderNotice = notificationMapper.senderNoticeMapper(newMessage);
        sender.getNotifications().add(senderNotice);
        userRepository.save(sender);


        Notification receiverNotice = notificationMapper.receiverNoticeMapper(newMessage);

        for (User receiver : receivers) {
            receiver.getNotifications().add(receiverNotice);
            userRepository.save(receiver);
        }

        SentMessageResponse response = new SentMessageResponse();
        response.setNotificationMessage("Mail Sent to " + newMessage.getReceiverEmail() + " from " + newMessage.getSenderEmail());
        response.setCreationTime(LocalDateTime.now());
        return response;
    }

    @Override
    public MailBox viewMailBox(ViewBoxRequest viewRequest) throws UserAccountException {
        String theType = (viewRequest.getMailType()).toLowerCase();
        User theUser = userRepository.findUsersByEmailAddress(viewRequest.getEmailAddress());
        if (!(userRepository.existsByEmailAddress(viewRequest.getEmailAddress()))) {
            throw new UserAccountException("Account with this email does not exist");
        }
        MailBox theMailBox = new MailBox();
        boolean login = theUser.getPassword().equals(viewRequest.getPassword());
        if (login) {
            MailBoxes theUserMailBox = mailBoxesRepository.findUserMailBoxByUserID(viewRequest.getEmailAddress());

            if (theType.equals("sent")) {
                theMailBox = theUserMailBox.getMessageBoxes().get(0);
                return theMailBox;
            }
            if (theType.equals("inbox")) {
                theMailBox = theUserMailBox.getMessageBoxes().get(1);
                return theMailBox;
            }
        } else {
            throw new UserAccountException("Invalid detail");
        }
        return theMailBox;
    }

    @Override
    public void deleteMailBoxes(String emailAddress) throws UserAccountException {
        MailBoxes selectedMailBoxes = mailBoxesRepository.findUserMailBoxByUserID(emailAddress);
        if (selectedMailBoxes == null) throw new UserAccountException("Account not found");
        MailBox sentBox = selectedMailBoxes.getMessageBoxes().get(0);
        MailBox inbox = selectedMailBoxes.getMessageBoxes().get(1);
        mailBoxService.deleteMailbox(sentBox.getUserID());
        mailBoxService.deleteMailbox(inbox.getUserID());
        mailBoxesRepository.delete(selectedMailBoxes);
    }

    @Override
    public SentMessageResponse forwardMessageToReceiver(ForwardMessageRequest messageRequest) throws UserAccountException {
        User sender = userRepository.findUsersByEmailAddress(messageRequest.getSenderEmail());
        if (!(userRepository.existsByEmailAddress(messageRequest.getSenderEmail()))) {
            throw new UserAccountException("Account with this email does not exist");
        }

        List<User> receivers = new ArrayList<>();
        for (String receiverEmail : messageRequest.getReceiversEmail()) {
            User receiver = userRepository.findUsersByEmailAddress(receiverEmail);
            if (!(userRepository.existsByEmailAddress(receiverEmail))) {
                throw new UserAccountException("Account with this email does not exist");
            }
            receivers.add(receiver);
        }
        MailBoxes senderUserMailBox = mailBoxesRepository.findUserMailBoxByUserID(messageRequest.getSenderEmail());
        MailBox senderSent = senderUserMailBox.getMessageBoxes().get(0);
        MailBox senderInbox = senderUserMailBox.getMessageBoxes().get(1);

        List <MailBoxes> theReceiversMailBoxes = new ArrayList<>();
        for (User receiver : receivers){
            theReceiversMailBoxes.add(mailBoxesRepository.findUserMailBoxByUserID(receiver.getEmailAddress()));
        }

        List<MailBox> receiversInboxes = new ArrayList<>();
        for (MailBoxes receiverMailBoxes : theReceiversMailBoxes) {
            receiversInboxes.add(receiverMailBoxes.getMessageBoxes().get(1));
        }

        Message  theFrowardMessage = null;
        if(senderInbox.getMessages().contains(messageRequest.getMessageID())){
           Message frowardMessage = messageService.findMessage(messageRequest.getMessageID());
            frowardMessage.setMessageID(null);
            frowardMessage.getReceiverEmail().addAll(messageRequest.getReceiversEmail());
            frowardMessage.setCreationTime(LocalDateTime.now());
            frowardMessage.setSenderEmail(messageRequest.getSenderEmail());
            theFrowardMessage = messageRepository.save(frowardMessage);
            senderSent.getMessages().add(theFrowardMessage);
            for (MailBox receiverInbox : receiversInboxes){
                receiverInbox.getMessages().add(theFrowardMessage);
            }

            mailBoxRepository.save(senderSent);
            mailBoxRepository.saveAll(receiversInboxes);
        }
        else if(senderSent.getMessages().contains(messageRequest.getMessageID())){
            Message frowardMessage2  = messageService.findMessage(messageRequest.getMessageID());
            frowardMessage2.setMessageID(null);
            frowardMessage2.getReceiverEmail().addAll(messageRequest.getReceiversEmail());
            frowardMessage2.setCreationTime(LocalDateTime.now());
            frowardMessage2.setSenderEmail(messageRequest.getSenderEmail());
            theFrowardMessage = messageRepository.save(frowardMessage2);
            senderSent.getMessages().add(theFrowardMessage);
            for (MailBox receiverInbox : receiversInboxes){
                receiverInbox.getMessages().add(theFrowardMessage);
            }

            mailBoxRepository.save(senderSent);
            mailBoxRepository.saveAll(receiversInboxes);
        }
        Notification senderNotice = notificationMapper.senderNoticeMapper(theFrowardMessage);
        sender.getNotifications().add(senderNotice);
        userRepository.save(sender);

        Notification receiverNotice = notificationMapper.receiverNoticeMapper(theFrowardMessage);
        for (User receiver : receivers) {
            receiver.getNotifications().add(receiverNotice);
            userRepository.save(receiver);
        }

        SentMessageResponse response = new SentMessageResponse();
        response.setNotificationMessage("Mail Sent to " + theFrowardMessage.getReceiverEmail() + " from " + theFrowardMessage.getSenderEmail());
        response.setCreationTime(LocalDateTime.now());
        return response;
        }




    }



