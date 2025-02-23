package com.techproed.controller.user;

import com.techproed.payload.requests.user.StudentRequest;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.payload.response.user.StudentResponse;
import com.techproed.service.user.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PreAuthorize(("hasAnyAuthority('Admin')"))
    @PostMapping("/save")
    public ResponseMessage<StudentResponse> save(StudentRequest studentRequest) {
        return studentService.save(studentRequest);
    }
}
