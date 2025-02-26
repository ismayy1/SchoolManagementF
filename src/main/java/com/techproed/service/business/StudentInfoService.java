package com.techproed.service.business;

import com.techproed.entity.concretes.business.EducationTerm;
import com.techproed.entity.concretes.business.Lesson;
import com.techproed.entity.concretes.business.StudentInfo;
import com.techproed.entity.concretes.user.User;
import com.techproed.entity.enums.Note;
import com.techproed.entity.enums.RoleType;
import com.techproed.payload.mappers.StudentInfoMapper;
import com.techproed.payload.messages.SuccessMessages;
import com.techproed.payload.requests.business.StudentInfoRequest;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.payload.response.business.StudentInfoResponse;
import com.techproed.repository.business.StudentInfoRepository;
import com.techproed.service.helper.MethodHelper;
import com.techproed.service.helper.StudentInfoHelper;
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
    private final StudentInfoHelper studentInfoHelper;
    private final StudentInfoMapper studentInfoMapper;

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
//        student should have only one student info for a lesson
        studentInfoHelper.validateLessonDuplication(studentInfoRequest.getStudentId(), lesson.getLessonName());
        Note note = studentInfoHelper.checkLetterGrade(studentInfoHelper.calculateAverageScore(
                studentInfoRequest.getMidtermExam(), studentInfoRequest.getFinalExam()));
//        mapping
        StudentInfo studentInfo = studentInfoMapper.mapStudentInfoRequestToSTudentInfo(
                studentInfoRequest, note,
                studentInfoHelper.calculateAverageScore(
                        studentInfoRequest.getMidtermExam(),
                        studentInfoRequest.getFinalExam()));
//        set missing props
        studentInfo.setStudent(student);
        studentInfo.setLesson(lesson);
        studentInfo.setEducationTerm(educationTerm);
        studentInfo.setTeacher(teacher);
        StudentInfo savedStudentInfo = studentInfoRepository.save(studentInfo);

        return ResponseMessage.<StudentInfoResponse>builder()
                .message(SuccessMessages.STUDENT_INFO_SAVE)
                .returnBody(studentInfoMapper.mapStudentInfoToStudentInfoResponse(savedStudentInfo))
                .build();
    }
}
