package com.techproed.service.user;

import com.techproed.entity.concretes.user.User;
import com.techproed.payload.mappers.UserMapper;
import com.techproed.payload.messages.SuccessMessages;
import com.techproed.payload.requests.user.UserRequest;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.payload.response.user.UserResponse;
import com.techproed.repository.user.UserRepository;
import com.techproed.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final UserRepository userRepository;


    public ResponseMessage<UserResponse> saveUser(UserRequest userRequest, String userRole) {
//        Validate unique properties
        uniquePropertyValidator.checkDuplication(
                userRequest.getUsername(),
                userRequest.getSsn(),
                userRequest.getPhoneNumber(),
                userRequest.getEmail());    // need to be placed in the same order as in the validation

//        DTO -> entity mapping
        User userToSave = userMapper.mapUserRequestToUser(userRequest, userRole);

//        Save operation
        User savedUser = userRepository.save(userToSave);

//        Entity -> DTO mapping
        return ResponseMessage
                .<UserResponse> builder()
                .message(SuccessMessages.USER_CREATE)
                .returnBody(userMapper.mapUserToUserResponse(savedUser))
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
