package com.tech9ners.emailservicesoftware.controllers;

import com.tech9ners.emailservicesoftware.Dtos.responses.ApiResponse;
import com.tech9ners.emailservicesoftware.exceptions.UserAccountException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;

@Slf4j
@ControllerAdvice
public class DiaryExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({UserAccountException.class})
    public ResponseEntity<Object> diaryApplicationExceptionHandler(UserAccountException exception, WebRequest request){
        ApiResponse apiResponse = ApiResponse.builder()
                .message(exception.getMessage())
                .isSuccessful(false)
                .statusCode(400)
                .build();
        return handleExceptionInternal(exception, apiResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception, WebRequest request) {
        ApiResponse response = new ApiResponse(exception.getMessage(),"You do not have permission to perform that action",false, 400);
        return new ResponseEntity<Object>(
                response , new HttpHeaders(), HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> exceptionHandler(Exception exception, WebRequest request){
        exception.printStackTrace();
        ApiResponse apiResponse = ApiResponse.builder()
                .message(exception.getMessage())
                .isSuccessful(false)
                .statusCode(400)
                .build();
        return handleExceptionInternal(exception, apiResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler({SQLException.class})
    public ResponseEntity<Object> sqlExceptionHandler(SQLException exception, WebRequest request){
        log.info("Here now now ");

        ApiResponse apiResponse = ApiResponse.builder()
                .message(exception.getMessage())
                .isSuccessful(false)
                .statusCode(400)
                .build();
        return handleExceptionInternal(exception, apiResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> constraintViolationExceptionHandler(ConstraintViolationException exception, WebRequest request){
        log.info("Here now");

        exception.printStackTrace();
        ApiResponse apiResponse = ApiResponse.builder()
                .message(exception.getMessage())
                .isSuccessful(false)
                .statusCode(400)
                .build();
        return handleExceptionInternal(exception, apiResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
