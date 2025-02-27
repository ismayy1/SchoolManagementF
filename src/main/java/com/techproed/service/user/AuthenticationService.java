package com.techproed.service.user;

import com.techproed.entity.concretes.user.User;
import com.techproed.exception.BadRequestException;
import com.techproed.payload.messages.ErrorMessages;
import com.techproed.payload.requests.authentication.LoginRequest;
import com.techproed.payload.requests.authentication.UpdatePasswordRequest;
import com.techproed.payload.response.authentication.AuthenticationResponse;
import com.techproed.repository.user.UserRepository;
import com.techproed.security.jwt.JwtUtils;
import com.techproed.security.service.UserDetailsImpl;
import com.techproed.service.helper.MethodHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MethodHelper methodHelper;

    public AuthenticationResponse authenticate(@Valid LoginRequest loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

//        injection of security in service
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
//        security authentication is done with the line below
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String userRole = userDetails.getAuthorities().iterator().next().getAuthority();

        return AuthenticationResponse.builder()
                .token(token)
                .role(userRole)
                .username(userDetails.getUsername())
                .build();
    }

    public void changePassword(
            @Valid UpdatePasswordRequest updatePasswordRequest,
            HttpServletRequest httpServletRequest) {

        String username = (String) httpServletRequest.getAttribute("username");
        User user = methodHelper.loadByUsername(username);
        methodHelper.checkBuildIn(user);
//        validate new password doesn't match to old password
        if (passwordEncoder.matches(updatePasswordRequest.getNewPassword(), user.getPassword())) {
            throw new BadRequestException(ErrorMessages.PASSWORD_SHOULD_NOT_MATCHED);
        }

//        user password can be updated via custom query
//        Or password can be set and user will be saved to DB again
        user.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
        userRepository.save(user);
    }
}
