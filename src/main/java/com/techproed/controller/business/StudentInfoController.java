package com.techproed.controller.business;

import com.techproed.payload.requests.business.StudentInfoRequest;
import com.techproed.payload.requests.business.StudentInfoUpdateRequest;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.payload.response.business.StudentInfoResponse;
import com.techproed.service.business.StudentInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/studentInfo")
@RequiredArgsConstructor
public class StudentInfoController {

    private final StudentInfoService studentInfoService;
    StudentInfoUpdateRequest studentInfoUpdateRequest;

    @PreAuthorize("hasAnyAuthority('Teacher')")
    @PostMapping("/save")
    public ResponseMessage<StudentInfoResponse> saveStudentInfo(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid StudentInfoRequest studentInfoRequest) {
        return studentInfoService.saveStudentInfo(httpServletRequest, studentInfoRequest);
    }

    @PreAuthorize("hasAnyAuthority('Admin','Teacher')")
    @PutMapping("/update/{id}")
    public ResponseMessage<StudentInfoResponse>updateStudentInfo(
            @RequestBody @Valid StudentInfoUpdateRequest studentInfoUpdateRequest,
            @PathVariable Long id){
        return studentInfoService.updateStudentInfo(studentInfoUpdateRequest,id);
    }

    @PreAuthorize("hasAnyAuthority('Admin','Teacher')")
    @DeleteMapping("/delete/{id}")
    public ResponseMessage delete(@PathVariable Long id){
        return studentInfoService.deleteStudentInfoById(id);
    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @GetMapping("/findByStudentId/{studentId}")
    public List<StudentInfoResponse> findStudentInfoByStudentId(@PathVariable Long studentId){
        return studentInfoService.findStudentInfoByStudentId(studentId);
    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @GetMapping("/findStudentInfoByPage")
    public Page<StudentInfoResponse> findStundentInfoByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "absentee") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type) {
        return studentInfoService.findStudentInfoByPage(page, size, sort, type);
    }


    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @GetMapping("/findById/{studentInfoId}")
    public ResponseEntity<StudentInfoResponse> getStudentInfoById(@PathVariable Long studentInfoId) {
        return ResponseEntity.ok( studentInfoService.findById(studentInfoId));
    }

    @PreAuthorize("hasAnyAuthority('Teacher','Student')")
    @GetMapping("/findByTeacherOrStudentByPage")
    public Page<StudentInfoResponse>findByTeacherOrStudentByPage(
            HttpServletRequest servletRequest,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size){
        return studentInfoService.findByTeacherOrStudentByPage(servletRequest,page,size);
    }
}
