package com.techproed.service.user;

import com.techproed.entity.concretes.user.User;
import com.techproed.payload.mappers.UserMapper;
import com.techproed.payload.messages.SuccessMessages;
import com.techproed.payload.requests.user.UserRequest;
import com.techproed.payload.requests.user.UserRequestWithoutPassword;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    public ResponseMessage<UserResponse> updateUserById(UserRequest userRequest, Long userId) {
//        Validate if user exists
        User userFromDB = methodHelper.isUserExist(userId);

//        BuildIn users cannot be updated
        methodHelper.checkBuildIn(userFromDB);

//        Unique properties Validation
        uniquePropertyValidator.checkUniqueProperty(userFromDB, userRequest);

//        Mapping
        User userToSave = userMapper.mapUserRequestToUser(userRequest, userFromDB.getUserRole().getRoleName());
        userToSave.setId(userId);

        User savedUser = userRepository.save(userToSave);

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_UPDATE)
                .httpStatus(HttpStatus.OK)
                .returnBody(userMapper.mapUserToUserResponse(savedUser))
                .build();
    }

    public String updateLoggedInUser(
            UserRequestWithoutPassword userRequestWithoutPassword,
            HttpServletRequest httpServletRequest) {

        String username = (String) httpServletRequest.getAttribute("username");
        User user = userRepository.findByUsername(username);
        methodHelper.checkBuildIn(user);
        uniquePropertyValidator.checkUniqueProperty(user, userRequestWithoutPassword);

        user.setName(userRequestWithoutPassword.getName());
        user.setSsn(userRequestWithoutPassword.getSsn());
        user.setUsername(userRequestWithoutPassword.getUsername());
        user.setBirthday(userRequestWithoutPassword.getBirthDay());
        user.setBirthplace(userRequestWithoutPassword.getBirthPlace());
        user.setEmail(userRequestWithoutPassword.getEmail());
        user.setPhoneNumber(userRequestWithoutPassword.getPhoneNumber());
        user.setGender(userRequestWithoutPassword.getGender());
        userRepository.save(user);

        return SuccessMessages.USER_UPDATE;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
