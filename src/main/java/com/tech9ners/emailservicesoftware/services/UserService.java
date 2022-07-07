package com.tech9ners.emailservicesoftware.services;

import com.tech9ners.emailservicesoftware.Dtos.requests.DeleteRequest;
import com.tech9ners.emailservicesoftware.Dtos.requests.LoginRequest;
import com.tech9ners.emailservicesoftware.Dtos.requests.RegisterUserRequest;
import com.tech9ners.emailservicesoftware.Dtos.responses.DeleteUserResponse;
import com.tech9ners.emailservicesoftware.Dtos.responses.FindUserResponse;
import com.tech9ners.emailservicesoftware.Dtos.responses.LoginUserResponse;
import com.tech9ners.emailservicesoftware.Dtos.responses.RegisterResponse;
import com.tech9ners.emailservicesoftware.exceptions.UserAccountException;

public interface UserService {
    RegisterResponse createUser(RegisterUserRequest userRequest) throws UserAccountException;

    LoginUserResponse userLogin(LoginRequest login) throws UserAccountException;

    long count();

    FindUserResponse findByAccountEmailId(String emailAddress) throws UserAccountException;

    DeleteUserResponse deleteUser(DeleteRequest request) throws UserAccountException;
}
