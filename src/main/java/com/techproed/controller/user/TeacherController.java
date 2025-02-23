package com.techproed.controller.user;

import com.techproed.payload.requests.user.TeacherRequest;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.payload.response.user.UserResponse;
import com.techproed.service.user.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PostMapping("/save")
    public ResponseMessage<UserResponse> saveTeacher(@RequestBody @Valid TeacherRequest teacherRequest) {
        return teacherService.saveTeacher(teacherRequest);
    }
}
