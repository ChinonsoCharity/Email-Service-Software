package com.tech9ners.emailservicesoftware.services;

import com.tech9ners.emailservicesoftware.Dtos.requests.DeleteRequest;
import com.tech9ners.emailservicesoftware.Dtos.requests.MessageRequest;
import com.tech9ners.emailservicesoftware.Dtos.requests.RegisterUserRequest;
import com.tech9ners.emailservicesoftware.data.repositories.MailBoxRepository;
import com.tech9ners.emailservicesoftware.data.repositories.UserRepository;
import com.tech9ners.emailservicesoftware.exceptions.UserAccountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
class UserServiceImplTest {
    @Autowired
    private UserService service;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MailBoxesService userMailBoxService;
    @Autowired
    MailBoxService mailBoxService;
    @Autowired
    MailBoxRepository mailBoxRepository;

    RegisterUserRequest request;
    RegisterUserRequest request2;
    RegisterUserRequest request3;

    @BeforeEach
    void setUp() {
        request = new RegisterUserRequest();
        request2 = new RegisterUserRequest();
        request3 = new RegisterUserRequest();
    }

//    @AfterEach()
//    void tearDown() {
//        userRepository.deleteAll();
//    }

    @Test
    void registerUser() throws UserAccountException {
    request.setLastName("james");
    request.setFirstName("adams");
    request.setEmailAddress("adams@watmail.com");
    request.setPassword("Jakings123#");

        var result= service.createUser(request);
        assertEquals(1L, service.count());
        assertThat(result.getEmailAddress(),is("adams@watmail.com"));
        assertThat(result.getMessage(),is("Registration Successful"));

    }
    @Test
    void canDeleteUser() throws UserAccountException {
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

//        MessageRequest messageRequest = new MessageRequest();
//        messageRequest.setSenderEmail("adams@watmail.com");
//        messageRequest.setReceiverEmail("ven@watmail.com");
//        messageRequest.setSubject("Introduction");
//        messageRequest.setMessageBody("Welcome to WatEmail,where mail speed is guaranteed");
//        userMailBoxService.sentMessage(messageRequest);

        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.setEmailAddress("ven@watmail.com");
        deleteRequest.setPassword("Jvkings123#");

        var result = service.deleteUser(deleteRequest);
        assertThat(result.getMessage(),is("Account Deleted Successfully"));
    }

}