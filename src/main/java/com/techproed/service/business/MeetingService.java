package com.techproed.service.business;

import com.techproed.entity.concretes.business.Meet;
import com.techproed.entity.concretes.user.User;
import com.techproed.payload.mappers.MeetingMapper;
import com.techproed.payload.messages.SuccessMessages;
import com.techproed.payload.requests.business.MeetingRequest;
import com.techproed.payload.response.business.MeetingResponse;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.repository.business.MeetingRepository;
import com.techproed.service.helper.MeetingHelper;
import com.techproed.service.helper.MethodHelper;
import com.techproed.service.helper.PageableHelper;
import com.techproed.service.validator.TimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final TimeValidator timeValidator;
    private final MeetingMapper meetingMapper;
    private final PageableHelper pageableHelper;
    private final MethodHelper methodHelper;
    private final MeetingHelper meetingHelper;

    public ResponseMessage<MeetingResponse> save(
            HttpServletRequest httpServletRequest, @Valid MeetingRequest meetingRequest) {

        String username = (String) httpServletRequest.getAttribute("username");
        User teacher = methodHelper.loadByUsername(username);
        methodHelper.checkIsAdvisor(teacher);
        timeValidator.checkStartIsBeforeStop(meetingRequest.getStartTime(), meetingRequest.getStopTime());

        meetingHelper.checkMeetingConflicts(
                meetingRequest.getStudentIds(),
                teacher.getId(),
                meetingRequest.getDate(),
                meetingRequest.getStartTime(),
                meetingRequest.getStopTime());

        List<User> students = methodHelper.getUserList(meetingRequest.getStudentIds());
        Meet meetToSave = meetingMapper.mapMeetingRequestToMeet(meetingRequest);
        meetToSave.setStudentList(students);
        meetToSave.setAdvisoryTeacher(teacher);
        Meet savedMeeting = meetingRepository.save(meetToSave);

        return ResponseMessage.<MeetingResponse>builder()
                .message(SuccessMessages.MEET_SAVE)
                .returnBody(meetingMapper.mapMeetingToMeetingResponse(savedMeeting))
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    public ResponseMessage<MeetingResponse> update(
            @Valid MeetingRequest meetingRequest,
            Long meetingId,
            HttpServletRequest httpServletRequest) {

        Meet existingMeeting = meetingHelper.isMeetingExistById(meetingId);
//        validate is logged in teacher owner of this meeting
        meetingHelper.isMeetingMatchedWithTeacher(existingMeeting, httpServletRequest);
        timeValidator.checkStartIsBeforeStop(meetingRequest.getStartTime(), meetingRequest.getStopTime());
        meetingHelper.checkMeetingConflicts(
                meetingRequest.getStudentIds(),
                existingMeeting.getAdvisoryTeacher().getId(),
                meetingRequest.getDate(),
                meetingRequest.getStartTime(),
                meetingRequest.getStopTime());
        List<User> students = methodHelper.getUserList(meetingRequest.getStudentIds());
        Meet meetingToUpdate = meetingMapper.mapMeetingRequestToMeet(meetingRequest);
        meetingToUpdate.setStudentList(students);
        meetingToUpdate.setAdvisoryTeacher(existingMeeting.getAdvisoryTeacher());
        meetingToUpdate.setId(existingMeeting.getId());
        Meet updateMeeting = meetingRepository.save(meetingToUpdate);

        return ResponseMessage.<MeetingResponse>builder()
                .message(SuccessMessages.MEET_UPDATE)
                .returnBody(meetingMapper.mapMeetingToMeetingResponse(updateMeeting))
                .build();
    }

    public String deleteById(
            Long meetingId,
            HttpServletRequest httpServletRequest) {
        //validations
        Meet existingMeeting = meetingHelper.isMeetingExistById(meetingId);
        String username = (String) httpServletRequest.getAttribute("username");
        User user = methodHelper.loadByUsername(username);
        if (!user.getUserRole().getRoleName().equalsIgnoreCase("Admin")) {
            meetingHelper.isMeetingMatchedWithTeacher(existingMeeting, httpServletRequest);
        }
        //delete
        meetingRepository.deleteById(meetingId);
        return SuccessMessages.MEET_DELETE;
    }

    public ResponseEntity<ResponseMessage<Page<MeetingResponse>>> getAllByPageTeacher(
            int page, int size,
            HttpServletRequest httpServletRequest) {
        String username = (String) httpServletRequest.getAttribute("username");
        User teacher = methodHelper.loadByUsername(username);
        Pageable pageable = pageableHelper.getPageableByPageAndSize(page,size);
        Page<Meet> meetings = meetingRepository.findByAdvisoryTeacher_Id(teacher.getId(), pageable);
        Page<MeetingResponse> meetingResponses = meetings.map(meetingMapper::mapMeetingToMeetingResponse);
        ResponseMessage<Page<MeetingResponse>> responseMessage =
                ResponseMessage.<Page<MeetingResponse>>builder()
                        .message(SuccessMessages.MEET_FOUND)
                        .returnBody(meetingResponses)
                        .httpStatus(HttpStatus.OK)
                        .build();
        return ResponseEntity.status(responseMessage.getHttpStatus()).body(responseMessage);
    }

    public Page<MeetingResponse> getAllByPage(int page, int size) {

        Pageable pageable = pageableHelper.getPageableByPageAndSize(page, size);
        Page<Meet> meets =meetingRepository.findAll(pageable);
        return meets.map(meetingMapper::mapMeetingToMeetingResponse);
    }
}
