package com.techproed.payload.mappers;

import com.techproed.entity.concretes.user.User;
import com.techproed.entity.enums.RoleType;
import com.techproed.exception.ResourceNotFoundException;
import com.techproed.payload.messages.ErrorMessages;
import com.techproed.payload.requests.user.UserRequest;
import com.techproed.payload.response.user.UserResponse;
import com.techproed.service.user.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final UserRoleService userRoleService;

    /**
     *
     * @param userRequest DTO from postman or FE
     * @param userRole role of user to be created or updated
     * @return User entity
     */

    public User mapUserRequestToUser(UserRequest userRequest, String userRole) {
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

//        Because role and user has one-to-one relationship,
//        we need to fetch it from DB and put it onto the request

        if (userRole.equalsIgnoreCase(RoleType.ADMIN.getName())) {
//            if username is ADMIN, database can't be changed
            if (Objects.equals(userRequest.getUsername(), "Admin")) {
                user.setBuildIn(true);
            }
            user.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));

        } else if (userRole.equalsIgnoreCase(RoleType.MANAGER.getName())) {
            user.setUserRole(userRoleService.getUserRole(RoleType.MANAGER));
        } else if (userRole.equalsIgnoreCase(RoleType.ASSISTANT_MANAGER.getName())) {
            user.setUserRole(userRoleService.getUserRole(RoleType.ASSISTANT_MANAGER));
        } else if (userRole.equalsIgnoreCase(RoleType.STUDENT.getName())) {
            user.setUserRole(userRoleService.getUserRole(RoleType.STUDENT));
        } else if (userRole.equalsIgnoreCase(RoleType.TEACHER.getName())) {
            user.setUserRole(userRoleService.getUserRole(RoleType.TEACHER));
        } else {
            throw new ResourceNotFoundException(
                    String.format(ErrorMessages.NOT_FOUND_USER_USER_ROLE_MESSAGE, userRole));
        }
        return user;
    }

    public UserResponse mapUserToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .birthDay(user.getBirthday())
                .birthPlace(user.getBirthplace())
                .ssn(user.getSsn())
                .email(user.getEmail())
                .userRole(user.getUserRole().getRoleType().getName())
                .build();
    }
}
