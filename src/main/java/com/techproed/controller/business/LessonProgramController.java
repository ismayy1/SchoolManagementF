package com.techproed.controller.business;

import com.techproed.payload.requests.business.LessonProgramRequest;
import com.techproed.payload.response.LessonProgramResponse;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.service.business.LessonProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lessonProgram")
@RequiredArgsConstructor
public class LessonProgramController {

    private final LessonProgramService lessonProgramService;

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @PostMapping("/save")
    public ResponseMessage<LessonProgramResponse> saveLessonProgram(
            @RequestBody @Valid LessonProgramRequest lessonProgramRequest) {
        return lessonProgramService.saveLessonProgram(lessonProgramRequest);
    }

//    TODO
    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @GetMapping("/gteAll")
    public List<LessonProgramResponse> getAllLessonPrograms() {
//        return lessonProgramService.getAllLessonPrograms();
        return null;
    }

//    TODO
    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @GetMapping("/getLessonProgram/{id}")
    public List<LessonProgramResponse> getLessonProgramById(@PathVariable Long id) {
//        return lessonProgramService.findById(id);
        return null;
    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @GetMapping("/getAllUnassigned")
    public List<LessonProgramResponse> getAllUnassignedLessonPrograms() {
        return lessonProgramService.getAllUnassigned();
    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @GetMapping("/getAllAssigned")
    public List<LessonProgramResponse> getAllAssignedLessonPrograms() {
        return lessonProgramService.getAllAssigned();
    }

//    TODO ESRA
    @PreAuthorize("hasAnyAuthority('Admin','Teacher')")
    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteLessonProgramById(@PathVariable Long id){
        //return lessonProgramService.deleteLessonProgramById(id);
        return null;
    }
}
