package com.tech9ners.emailservicesoftware.services;

import com.tech9ners.emailservicesoftware.Dtos.requests.DeleteRequest;
import com.tech9ners.emailservicesoftware.Dtos.requests.LoginRequest;
import com.tech9ners.emailservicesoftware.Dtos.requests.RegisterUserRequest;
import com.tech9ners.emailservicesoftware.Dtos.responses.DeleteUserResponse;
import com.tech9ners.emailservicesoftware.Dtos.responses.FindUserResponse;
import com.tech9ners.emailservicesoftware.Dtos.responses.LoginUserResponse;
import com.tech9ners.emailservicesoftware.Dtos.responses.RegisterResponse;
import com.tech9ners.emailservicesoftware.data.models.MailBox;
import com.tech9ners.emailservicesoftware.data.models.MailType;
import com.tech9ners.emailservicesoftware.data.models.User;
import com.tech9ners.emailservicesoftware.data.models.MailBoxes;
import com.tech9ners.emailservicesoftware.data.repositories.MailBoxesRepository;
import com.tech9ners.emailservicesoftware.data.repositories.UserRepository;
import com.tech9ners.emailservicesoftware.exceptions.UserAccountException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    MailBoxesService userMailBoxService;
    @Autowired
    MailBoxesRepository mailBoxesRepository;
    @Autowired
    MailBoxService mailBoxService;
    @Override
    public RegisterResponse createUser(RegisterUserRequest userRequest) throws UserAccountException {
        User theUser = userRepository.findUsersByEmailAddress(userRequest.getEmailAddress());
        if (userRepository.existsByEmailAddress(userRequest.getEmailAddress())) {
            throw new UserAccountException("An Account exist with this email");
        }
        User newUser = new User();
        newUser.setLastName(userRequest.getLastName());
        newUser.setFirstName(userRequest.getFirstName());
        newUser.setEmailAddress(userRequest.getEmailAddress());
        newUser.setPassword(userRequest.getPassword());

        MailBox sentMailBox = mailBoxService.createMailBox("Hi "+ userRequest.getFirstName(), MailType.Sent);
        MailBox inboxMailBox = mailBoxService.createMailBox("Hi "+ userRequest.getFirstName(),MailType.Inbox);

        MailBoxes newUserMailBox = userMailBoxService.createUserMailBox(newUser.getEmailAddress(),sentMailBox,inboxMailBox);

        userRepository.save(newUser);

        RegisterResponse response = new RegisterResponse();
        response.setEmailAddress(newUser.getEmailAddress());
        response.setMessage("Registration Successful");
                return response;
    }

    public LoginUserResponse userLogin(LoginRequest login) throws UserAccountException {
        User theUser = userRepository.findUsersByEmailAddress(login.getEmailAddress());
        if (theUser == null) throw new UserAccountException("Account not found");
        boolean credentialsMatch = theUser.getPassword().equals(login.getPassword());
        if (credentialsMatch) System.out.println ("Login Successful");
        else throw new UserAccountException("Invalid details");

        MailBoxes theUserMailBox = mailBoxesRepository.findUserMailBoxByUserID(login.getEmailAddress());

        LoginUserResponse response = new LoginUserResponse();
        response.setLoginMessage("Welcome "+theUser.getFirstName());
        response.setNotifications(theUser.getNotifications());
        response.setUserMailBox(theUserMailBox);
        return response;
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public FindUserResponse findByAccountEmailId(String emailAddress) throws UserAccountException {
        User theUser = userRepository.findUsersByEmailAddress(emailAddress);
        if (!(userRepository.existsByEmailAddress(emailAddress)))
            throw new UserAccountException("Account not found");
        FindUserResponse response = new FindUserResponse();
        response.setFindEmailAddress(theUser.getEmailAddress());
        response.setFullName(theUser.getLastName().concat(theUser.getFirstName()));
        response.setFindNotifications(theUser.getNotifications());
        return response;
    }

    @Override
    public DeleteUserResponse deleteUser(DeleteRequest request) throws UserAccountException {
        User selectedUser = userRepository.findUsersByEmailAddress(request.getEmailAddress());
        if (!(userRepository.existsByEmailAddress(request.getEmailAddress())))throw new UserAccountException("Account with this email does not exist");

        boolean check = selectedUser.getPassword().equals(request.getPassword());

        if (check){
            userMailBoxService.deleteMailBoxes(request.getEmailAddress());
            userRepository.delete(selectedUser);
        }else throw new UserAccountException("Invalid Details");

        DeleteUserResponse response = new DeleteUserResponse();
        response.setMessage("Account Deleted Successfully");
        return response;
    }
}
