package com.techproed.payload.mappers;

import com.techproed.entity.concretes.user.User;
import com.techproed.payload.requests.user.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public User mapUserRequestToUser(UserRequest userRequest) {
        User user = User.builder()
                .username(userRequest.getUsername())
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .password(userRequest.getPassword())
                .ssn(userRequest.getSsn())
                .birthday(userRequest.getBirthDay())
                .birthplace(userRequest.getBirthPlace())
                .phoneNumber(userRequest.getPhoneNumber())
                .gender(userRequest.getGender())
                .email(userRequest.getEmail())
                .buildIn(userRequest.getBuildIn())
                .isActive(false)
                .build();
    }
}
