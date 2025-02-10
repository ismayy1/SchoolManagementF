package com.techproed.controller.user;

import com.techproed.payload.requests.user.UserRequest;
import com.techproed.payload.response.abstracts.BaseUserResponse;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.payload.response.user.UserResponse;
import com.techproed.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/save/{userRole}")
    public ResponseEntity<ResponseMessage<UserResponse>> saveUser (@RequestBody @Valid UserRequest userRequest, @PathVariable String userRole) {
        return ResponseEntity.ok(userService.saveUser(userRequest, userRole));
    }

    @GetMapping("/getUserByPage/{userRole}")
    public ResponseEntity<Page<UserResponse>> getUserByPage(
            @PathVariable String userRole,
            @RequestParam (value = "page", defaultValue = "0") int page,
            @RequestParam (value = "size", defaultValue = "10") int size,
            @RequestParam (value = "sort", defaultValue = "name") String sort,
            @RequestParam (value = "type", defaultValue = "desc") String type) {

        Page<UserResponse> userResponses = userService.getUserByPage(page, size, sort, type, userRole);
        return ResponseEntity.ok(userResponses);
    }

    @GetMapping("getUserById/{userId}")
    public ResponseMessage<BaseUserResponse> getUser(@PathVariable Long userId) {
        return userService.findUserById(userId);
    }

    @DeleteMapping("/deleteUserById/{userId}")
    public ResponseEntity<String> deleteUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.deleteUserById(userId));
    }
}
