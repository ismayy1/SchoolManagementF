package com.techproed.service.user;

import com.techproed.entity.concretes.user.User;
import com.techproed.payload.mappers.UserMapper;
import com.techproed.payload.messages.SuccessMessages;
import com.techproed.payload.requests.user.UserRequest;
import com.techproed.payload.response.abstracts.BaseUserResponse;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.payload.response.user.UserResponse;
import com.techproed.repository.user.UserRepository;
import com.techproed.service.helper.MethodHelper;
import com.techproed.service.helper.PageableHelper;
import com.techproed.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final MethodHelper methodHelper;
    private final PageableHelper pageableHelper;


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

    public ResponseMessage<BaseUserResponse> findUserById(Long userId) {
//        Validate if the user exist in DB
        User user = methodHelper.isUserExist(userId);

        return ResponseMessage.<BaseUserResponse>builder()
                .message(SuccessMessages.USER_FOUND)
                .returnBody(userMapper.mapUserToUserResponse(user))
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public String deleteUserById(Long userId) {
//        Validate if the user exist in DB
        methodHelper.isUserExist(userId);

//        Delete user from DB
        userRepository.deleteById(userId);

        return SuccessMessages.USER_DELETE;
    }

    public Page<UserResponse> getUserByPage(int page, int size, String sort, String type, String userRole) {
        Pageable pageable = pageableHelper.getPageable(page, size, sort, type);

        return userRepository.findUserByUserRoleQuery(userRole, pageable)
                .map(userMapper::mapUserToUserResponse);
    }
}
