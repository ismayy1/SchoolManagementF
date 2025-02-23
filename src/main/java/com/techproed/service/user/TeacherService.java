package com.techproed.service.user;

import com.techproed.entity.concretes.business.LessonProgram;
import com.techproed.entity.concretes.user.User;
import com.techproed.entity.enums.RoleType;
import com.techproed.payload.mappers.UserMapper;
import com.techproed.payload.messages.SuccessMessages;
import com.techproed.payload.requests.user.TeacherRequest;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.payload.response.user.UserResponse;
import com.techproed.repository.user.UserRepository;
import com.techproed.service.business.LessonProgramService;
import com.techproed.service.helper.MethodHelper;
import com.techproed.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MethodHelper methodHelper;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final LessonProgramService lessonProgramService;

    public ResponseMessage<UserResponse> saveTeacher(TeacherRequest teacherRequest) {

        List<LessonProgram> lessonProgramList =
                lessonProgramService.getLessonProgramById(teacherRequest.getLessonProgramList());

//        validate unique props
        uniquePropertyValidator.checkDuplication(
                teacherRequest.getUsername(),
                teacherRequest.getSsn(),
                teacherRequest.getPhoneNumber(),
                teacherRequest.getEmail());

        User teacher = userMapper.mapUserRequestToUser(teacherRequest, RoleType.TEACHER.getName());
//        set additional props
        teacher.setIsAdvisor(teacherRequest.getIsAdvisoryTeacher());
        teacher.setLessonProgramList(lessonProgramList);
        User savedTeacher = userRepository.save(teacher);

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.TEACHER_SAVE)
                .httpStatus(HttpStatus.CREATED)
                .returnBody(userMapper.mapUserToUserResponse(savedTeacher))
                .build();
    }
}
