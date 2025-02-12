package com.techproed.service.validator;

import com.techproed.entity.concretes.user.User;
import com.techproed.exception.ConflictException;
import com.techproed.payload.messages.ErrorMessages;
import com.techproed.payload.requests.abstracts.AbstractUserRequest;
import com.techproed.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {

    private final UserRepository userRepository;

    public void checkUniqueProperty(User user, AbstractUserRequest userRequest) {
        String updatedUserName = "";
        String updatedSsn = "";
        String updatedEmail = "";
        String updatedPhone = "";
        boolean isChanged = false;

//        We're checking that if we changed the unique properties
        if (!user.getUsername().equals(userRequest.getUsername())) {
            updatedUserName = userRequest.getUsername();
            isChanged = true;
        }
        if (!user.getSsn().equals(userRequest.getSsn())) {
            updatedSsn = userRequest.getSsn();
            isChanged = true;
        }
        if (!user.getEmail().equals(userRequest.getEmail())) {
            updatedEmail = userRequest.getEmail();
            isChanged = true;
        }
        if (!user.getPhoneNumber().equals(userRequest.getPhoneNumber())) {
            updatedPhone = userRequest.getPhoneNumber();
            isChanged = true;
        }
        if (isChanged) {
            checkDuplication(updatedUserName, updatedSsn, updatedPhone, updatedEmail);
        }
    }

    public void checkDuplication(
            String username, // + usernameFromDto
            String ssn,
            String phone,
            String email) {

        if (userRepository.existsByUsername(username)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_USERNAME, username));
        }
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_EMAIL, email));
        }
        if (userRepository.existsByPhoneNumber(phone)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER, phone));
        }
        if (userRepository.existsBySsn(username)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_SSN, ssn));
        }
    }
}
