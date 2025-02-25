package com.techproed.controller.business;

import com.techproed.payload.requests.business.MeetingRequest;
import com.techproed.payload.response.business.MeetingResponse;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.service.business.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/meeting")
@RestController
public class MeetingController {

    private final MeetingService meetingService;

    @PreAuthorize(("hasAnyAuthority('Teacher')"))
    @PutMapping("/save")
    public ResponseMessage<MeetingResponse> saveMeeting(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid MeetingRequest meetingRequest) {

        return meetingService.save(httpServletRequest, meetingRequest);
    }
}
