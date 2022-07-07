package com.tech9ners.emailservicesoftware.services;

import com.tech9ners.emailservicesoftware.Dtos.requests.DeleteRequest;
import com.tech9ners.emailservicesoftware.Dtos.requests.MessageRequest;
import com.tech9ners.emailservicesoftware.Dtos.requests.RegisterUserRequest;
import com.tech9ners.emailservicesoftware.Dtos.requests.ViewBoxRequest;
import com.tech9ners.emailservicesoftware.data.models.MailBox;
import com.tech9ners.emailservicesoftware.data.models.MailBoxes;
import com.tech9ners.emailservicesoftware.data.repositories.MailBoxRepository;
import com.tech9ners.emailservicesoftware.data.repositories.MailBoxesRepository;
import com.tech9ners.emailservicesoftware.data.repositories.UserRepository;
import com.tech9ners.emailservicesoftware.exceptions.UserAccountException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailBoxesServiceImplTest {
    @Autowired
    private UserService service;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MailBoxesService userMailBoxService;
    @Autowired
    MailBoxService mailBoxService;
    @Autowired
    MessageService messageService;
    @Autowired
    MailBoxRepository mailBoxRepository;
    @Autowired
    MailBoxesRepository mailBoxesRepository;

    RegisterUserRequest request;
    RegisterUserRequest request2;
    RegisterUserRequest request3;

    @BeforeEach
    void setUp() {
        request = new RegisterUserRequest();
        request2 = new RegisterUserRequest();
        request3 = new RegisterUserRequest();
    }

    @AfterEach()
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void canSentMessage() throws UserAccountException {
        request.setLastName("james");
        request.setFirstName("adams");
        request.setEmailAddress("adams@watmail.com");
        request.setPassword("Jakings123#");
        service.createUser(request);

        request2.setLastName("jude");
        request2.setFirstName("ven");
        request2.setEmailAddress("ven@watmail.com");
        request2.setPassword("Jvkings123#");
        service.createUser(request2);
//        var result = service.createUser(request);
        assertEquals(2L, service.count());

        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setSenderEmail("adams@watmail.com");
        messageRequest.getReceiverEmail().add("ven@watmail.com");
        messageRequest.setSubject("Introduction");
        messageRequest.setMessageBody("Welcome to WatEmail,where mail speed is guaranteed");

       var result = userMailBoxService.sentMessage(messageRequest);
        assertThat(result.getNotificationMessage(),is("Mail Sent to " +messageRequest.getReceiverEmail()+
                " from " +messageRequest.getSenderEmail()));
        var result2 = userRepository.findUsersByEmailAddress(messageRequest.getReceiverEmail().get(0));
        assertThat(result2.getNotifications().get(0).getNotificationMessage(),is("You have one new mail"));

    }
    @Test
    void canViewMailBox() throws UserAccountException {
        request.setLastName("james");
        request.setFirstName("adams");
        request.setEmailAddress("adams@watmail.com");
        request.setPassword("Jakings123#");
        service.createUser(request);

        request2.setLastName("jude");
        request2.setFirstName("ven");
        request2.setEmailAddress("ven@watmail.com");
        request2.setPassword("Jvkings123#");
        service.createUser(request2);
        assertEquals(2L, service.count());

        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setSenderEmail("adams@watmail.com");
        messageRequest.getReceiverEmail().add("ven@watmail.com");
        messageRequest.setSubject("Introduction");
        messageRequest.setMessageBody("Welcome to WatEmail,where mail speed is guaranteed");
        userMailBoxService.sentMessage(messageRequest);

        ViewBoxRequest boxRequest = new ViewBoxRequest();
        boxRequest.setEmailAddress("ven@watmail.com");
        boxRequest.setPassword("Jvkings123#");
        boxRequest.setMailType("sent");

//        var result = userMailBoxService.viewMailBox(boxRequest);
//        assertThat(result.getMessages().get(0).getSubject(),is("Introduction"));
        MailBox theMailBox = userMailBoxService.viewMailBox(boxRequest);
        MailBoxes theUserBox = mailBoxesRepository.findUserMailBoxByUserID(boxRequest.getEmailAddress());
        assertThat(theMailBox,is(theUserBox.getMessageBoxes().get(0)));
    }
    @Test
    void SearchByContent() throws UserAccountException {
        request.setLastName("james");
        request.setFirstName("adams");
        request.setEmailAddress("adams@watmail.com");
        request.setPassword("Jakings123#");
        service.createUser(request);

        request2.setLastName("jude");
        request2.setFirstName("ven");
        request2.setEmailAddress("ven@watmail.com");
        request2.setPassword("Jvkings123#");
        service.createUser(request2);
        assertEquals(2L, service.count());

        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setSenderEmail("adams@watmail.com");
        messageRequest.getReceiverEmail().add("ven@watmail.com");
        messageRequest.setSubject("Introduction");
        messageRequest.setMessageBody("Welcome to WatEmail,where this mail speed is guaranteed");
        userMailBoxService.sentMessage(messageRequest);

        MessageRequest messageRequest2 = new MessageRequest();
        messageRequest2.setSenderEmail("adams@watmail.com");
        messageRequest2.getReceiverEmail().add("ven@watmail.com");
        messageRequest2.setSubject("just checking");
        messageRequest2.setMessageBody("i am checking this method");
        userMailBoxService.sentMessage(messageRequest2);

        var result = messageService.findMessageByAnyWord("this");
        assertThat(result.get(0).getSubject(),is("Introduction"));
        assertThat(result.get(1).getSubject(),is("just checking"));
    }
}