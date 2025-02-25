package com.techproed.service.business;

import com.techproed.entity.concretes.user.User;
import com.techproed.payload.mappers.MeetingMapper;
import com.techproed.payload.requests.business.MeetingRequest;
import com.techproed.payload.response.business.MeetingResponse;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.repository.business.MeetingRepository;
import com.techproed.service.helper.MethodHelper;
import com.techproed.service.helper.PageableHelper;
import com.techproed.service.validator.TimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository repository;
    private final TimeValidator timeValidator;
    private final MeetingMapper meetingMapper;
    private final PageableHelper pageableHelper;
    private final MethodHelper methodHelper;

    public ResponseMessage<MeetingResponse> save(
            HttpServletRequest httpServletRequest, @Valid MeetingRequest meetingRequest) {

        String username = (String) httpServletRequest.getAttribute("username");
        User teacher = methodHelper.loadByUsername(username);
        methodHelper.checkIsAdvisor(teacher);
        timeValidator.checkStartIsBeforeStop(meetingRequest.getStartTime(), meetingRequest.getStopTime());

    }
}
