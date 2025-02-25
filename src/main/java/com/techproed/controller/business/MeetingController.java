package com.techproed.controller.business;

import com.techproed.payload.requests.user.StudentRequest;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.payload.response.user.StudentResponse;
import com.techproed.service.business.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/meeting")
@RestController
public class MeetingController {

    private final MeetingService meetingService;

    @PreAuthorize(("hasAnyAuthority('Teacher')"))
    @PutMapping("/save")
    public ResponseMessage<StudentResponse> saveMeeting(
            @PathVariable Long studentId,
            @RequestBody @Valid StudentRequest studentRequest) {


    }
}
