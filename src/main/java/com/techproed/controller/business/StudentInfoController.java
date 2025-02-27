package com.techproed.controller.business;

import com.techproed.payload.requests.business.StudentInfoRequest;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.payload.response.business.StudentInfoResponse;
import com.techproed.service.business.StudentInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/studentInfo")
@RequiredArgsConstructor
public class StudentInfoController {

    private final StudentInfoService studentInfoService;

    @PreAuthorize("hasAnyAuthority('Teacher')")
    @PostMapping("/save")
    public ResponseMessage<StudentInfoResponse> saveStudentInfo(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid StudentInfoRequest studentInfoRequest) {

        return studentInfoService.saveStudentInfo(httpServletRequest, studentInfoRequest);
    }
}
