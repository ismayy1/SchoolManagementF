package com.techproed.controller.user;

import com.techproed.payload.requests.business.AddLessonProgram;
import com.techproed.payload.requests.user.TeacherRequest;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.payload.response.user.StudentResponse;
import com.techproed.payload.response.user.UserResponse;
import com.techproed.service.user.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PostMapping("/save")
    public ResponseMessage<UserResponse> saveTeacher(@RequestBody @Valid TeacherRequest teacherRequest) {
        return teacherService.saveTeacher(teacherRequest);
    }


    @PreAuthorize("hasAnyAuthority('Admin')")
    @PutMapping("/update/{userId}")
    public ResponseMessage<UserResponse> updateTeacher(
            @RequestBody @Valid TeacherRequest teacherRequest,
            @PathVariable Long userId) {
        return teacherService.updateTeacherById(teacherRequest, userId);
    }

//    teacher will log-in then get all students who are assigned to him/her via lesson programs
    @PreAuthorize("hasAnyAuthority('Teacher')")
    @GetMapping("/getByAdvisorTeacher")
    public List<StudentResponse> getAllStudentByAdvisorTeacher(HttpServletRequest httpServletRequest) {
        return teacherService.getAllStudentByAdvisorTeacher(httpServletRequest);
    }

//    managers can add lesson programs to teacher

    @PreAuthorize("hasAnyAuthority('Admin', 'Dean', 'ViceDean')")
    @PostMapping("/addLessonProgram")
    public ResponseMessage<UserResponse> addLessonProgram (@RequestBody @Valid AddLessonProgram lessonProgram) {
        return teacherService.addLessonProgram(lessonProgram);
    }

//    TODO NESLIHAN
//    deleteTeacherById -> when the teacher is deleted, the corresponding
//    student's advisor teacher ID must be set to null

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @DeleteMapping("/deleteTeacherById/{teacherId}")
    public ResponseMessage<UserResponse> deleteTeacherById(@PathVariable Long teacherId){
        return teacherService.deleteTeacherById(teacherId);
    }

//    TODO -> not all users, only teachers
//    getAllByTeacher

//    TODO
//    getAllTeacherByPage
}
