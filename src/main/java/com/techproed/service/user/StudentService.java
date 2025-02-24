package com.techproed.service.user;

import com.techproed.entity.concretes.user.User;
import com.techproed.entity.enums.RoleType;
import com.techproed.payload.mappers.UserMapper;
import com.techproed.payload.messages.SuccessMessages;
import com.techproed.payload.requests.user.StudentRequest;
import com.techproed.payload.requests.user.StudentUpdateRequest;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.payload.response.user.StudentResponse;
import com.techproed.repository.user.UserRepository;
import com.techproed.service.business.LessonProgramService;
import com.techproed.service.helper.MethodHelper;
import com.techproed.service.validator.TimeValidator;
import com.techproed.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final MethodHelper methodHelper;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final LessonProgramService lessonProgramService;
    private final TimeValidator timeValidator;

    public ResponseMessage<StudentResponse> save(StudentRequest studentRequest) {

//        does advisor teacher exist in DB
        User advisorTeacher = methodHelper.isUserExist(studentRequest.getAdvisorTeacherId());
//        is she really advisor
        methodHelper.checkIsAdvisor(advisorTeacher);
//        validate unique properties
        uniquePropertyValidator.checkDuplication(
                studentRequest.getUsername(),
                studentRequest.getSsn(),
                studentRequest.getPhoneNumber(),
                studentRequest.getEmail());
//        map DTO to entity
        User student = userMapper.mapUserRequestToUser(studentRequest, RoleType.STUDENT.getName());
//        set missing props
        student.setAdvisorTeacherId(advisorTeacher.getId());
        student.setActive(true);
        student.setBuildIn(false);
//        every Student will have a number starting from 1000.
        student.setStudentNumber(getLastStudentNumber());
        User savedStudent = userRepository.save(student);

        return ResponseMessage.<StudentResponse>builder()
                .returnBody(userMapper.mapUserToStudentResponse(savedStudent))
                .message(SuccessMessages.STUDENT_SAVE)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    private int getLastStudentNumber() {
        if (!userRepository.findStudent()) {
            return 1000;
        }
        return userRepository.getMaxStudentNumber() + 1;
    }

    public String updateStudent(HttpServletRequest httpServletRequest, @Valid StudentUpdateRequest studentUpdateRequest) {

        String username = (String) httpServletRequest.getAttribute("username");
        User student = methodHelper.loadByUsername(username);
        uniquePropertyValidator.checkUniqueProperty(student, studentUpdateRequest);
        User userToUpdate = userMapper.mapStudentUpdateRequestToUser(studentUpdateRequest);
        userToUpdate.setId(student.getId());
        userToUpdate.setPassword(student.getPassword());
        userToUpdate.setBuildIn(student.getBuildIn());
        userToUpdate.setAdvisorTeacherId(student.getAdvisorTeacherId());
        userRepository.save(userToUpdate);

        return SuccessMessages.STUDENT_UPDATE;
    }
}
