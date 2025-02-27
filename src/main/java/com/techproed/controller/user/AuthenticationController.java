package com.techproed.controller.user;

import com.techproed.payload.messages.SuccessMessages;
import com.techproed.payload.requests.authentication.LoginRequest;
import com.techproed.payload.requests.authentication.UpdatePasswordRequest;
import com.techproed.payload.response.authentication.AuthenticationResponse;
import com.techproed.service.user.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(loginRequest));
    }

    @PreAuthorize(("hasAnyAuthority('Admin', 'Dean', 'ViceDean', 'Teacher', 'Student')"))
    @PostMapping("/changePassword")
    public ResponseEntity<String> updatePassword(
            @RequestBody @Valid UpdatePasswordRequest updatePasswordRequest,
            HttpServletRequest httpServletRequest) {

        authenticationService.changePassword(updatePasswordRequest, httpServletRequest);
        return ResponseEntity.ok(SuccessMessages.PASSWORD_CHANGED_RESPONSE_MESSAGE);
    }
}
