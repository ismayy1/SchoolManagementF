package com.techproed.controller.business;

import com.techproed.payload.requests.business.LessonRequest;
import com.techproed.payload.response.business.LessonResponse;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.service.business.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PreAuthorize("hasAnyAuthority('Admin', 'Dean', 'ViceDean')")
    @PostMapping("/save")
    public ResponseMessage<LessonResponse> saveLesson(
            @RequestBody @Valid LessonRequest lessonRequest) {

        return lessonService.saveLesson(lessonRequest);
    }
}
