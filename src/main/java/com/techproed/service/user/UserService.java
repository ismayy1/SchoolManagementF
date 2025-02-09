package com.techproed.service.user;

import com.techproed.payload.requests.user.UserRequest;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.payload.response.user.UserResponse;
import com.techproed.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UniquePropertyValidator uniquePropertyValidator;

    public ResponseMessage<UserResponse> saveUser(UserRequest userRequest, String userRole) {
//        Validate unique properties
        uniquePropertyValidator.checkDuplication(
                userRequest.getUsername(),
                userRequest.getSsn(),
                userRequest.getPhoneNumber(),
                userRequest.getEmail());    // need to be placed in the same order as in the validation
    }
}
