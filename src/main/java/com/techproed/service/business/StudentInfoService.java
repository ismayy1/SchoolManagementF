package com.techproed.service.business;

import com.techproed.entity.concretes.business.EducationTerm;
import com.techproed.entity.concretes.business.Lesson;
import com.techproed.entity.concretes.user.User;
import com.techproed.entity.enums.RoleType;
import com.techproed.payload.requests.business.StudentInfoRequest;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.payload.response.business.StudentInfoResponse;
import com.techproed.repository.business.StudentInfoRepository;
import com.techproed.service.helper.MethodHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class StudentInfoService {

    private final StudentInfoRepository studentInfoRepository;
    private final MethodHelper methodHelper;
    private final LessonService lessonService;
    private final EducationTermService educationTermService;

    public ResponseMessage<StudentInfoResponse> saveStudentInfo(
            HttpServletRequest httpServletRequest,
            @Valid StudentInfoRequest studentInfoRequest) {

        String teacherUserName = (String) httpServletRequest.getAttribute("username");
        User teacher = methodHelper.loadByUsername(teacherUserName);
//        validate student id
        User student = methodHelper.isUserExist(studentInfoRequest.getStudentId());
//        validate user is really a student
        methodHelper.checkUserRole(student, RoleType.STUDENT);
//        validate and fetch lesson
        Lesson lesson = lessonService.isLessonExistById(studentInfoRequest.getLessonId());
//        validate and fetch educationTerm
        EducationTerm educationTerm =
                educationTermService.isEducationTermExist(studentInfoRequest.getEducationTermId());
    }
}
