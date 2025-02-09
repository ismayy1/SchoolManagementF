package com.techproed.service.user;

import com.techproed.payload.requests.user.UserRequest;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.payload.response.user.UserResponse;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class UserService {
    public ResponseMessage<UserResponse> saveUser(@Valid UserRequest userRequest, String userRole) {
//        Validate unique properties

    }
}
