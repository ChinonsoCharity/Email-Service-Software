package com.tech9ners.emailservicesoftware.controllers;

import com.tech9ners.emailservicesoftware.Dtos.requests.LoginRequest;
import com.tech9ners.emailservicesoftware.Dtos.requests.MessageRequest;
import com.tech9ners.emailservicesoftware.Dtos.requests.RegisterUserRequest;
import com.tech9ners.emailservicesoftware.Dtos.requests.ViewBoxRequest;
import com.tech9ners.emailservicesoftware.Dtos.responses.ApiResponse;
import com.tech9ners.emailservicesoftware.Dtos.responses.LoginUserResponse;
import com.tech9ners.emailservicesoftware.Dtos.responses.RegisterResponse;
import com.tech9ners.emailservicesoftware.Dtos.responses.SentMessageResponse;
import com.tech9ners.emailservicesoftware.data.models.MailBox;
import com.tech9ners.emailservicesoftware.exceptions.UserAccountException;
import com.tech9ners.emailservicesoftware.services.MailBoxesService;
import com.tech9ners.emailservicesoftware.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("watEmail")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MailBoxesService mailBoxesService;


    @PostMapping("/user/create")
    public ResponseEntity<?> createUser(@RequestBody RegisterUserRequest request) throws UserAccountException {
        RegisterResponse response = userService.createUser(request);
        ApiResponse apiResponse = ApiResponse.builder()
                .payload(response)
                .isSuccessful(true)
                .message("Successful")
                .statusCode(201)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

    }

    @PostMapping("/sendMail")
    public ResponseEntity<?> sendMessage(@RequestBody MessageRequest request) throws UserAccountException {
        SentMessageResponse response = mailBoxesService.sentMessage(request);
        ApiResponse apiResponse = ApiResponse.builder()
                .payload(response)
                .isSuccessful(true)
                .message("Successful")
                .statusCode(201)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

    }

    @GetMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody LoginRequest login) throws UserAccountException {

        LoginUserResponse response = userService.userLogin(login);
        ApiResponse apiResponse = ApiResponse.builder()
//                .payload(response)
                .isSuccessful(true)
                .message("Successful")
                .statusCode(200)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }
    @GetMapping("/viewBoxes")
    public ResponseEntity<?> viewMailBox(@RequestBody ViewBoxRequest viewRequest) throws UserAccountException {

        MailBox userMailBox = mailBoxesService.viewMailBox(viewRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .payload(userMailBox)
                .isSuccessful(true)
                .message("Successful")
                .statusCode(200)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }


//    ERROR
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }));
        return errors;
    }
}

