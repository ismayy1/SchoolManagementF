package com.techproed.service.user;

import com.techproed.entity.concretes.user.UserRole;
import com.techproed.entity.enums.RoleType;
import com.techproed.repository.user.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRole getUserRole(RoleType roleType) {
        return userRoleRepository.findByUserRoleType(roleType)
                .orElseThrow(() -> new RuntimeException("UserRole Not Found!"));
    }
}
