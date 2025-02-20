package com.techproed.service.business;

import com.techproed.entity.concretes.business.EducationTerm;
import com.techproed.entity.concretes.business.Lesson;
import com.techproed.payload.requests.business.LessonProgramRequest;
import com.techproed.payload.response.LessonProgramResponse;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.repository.business.LessonProgramRepository;
import com.techproed.service.validator.TimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LessonProgramService {

    private final LessonProgramRepository lessonProgramRepository;
    private final LessonService lessonService;
    private final EducationTermService educationTermService;
    private final TimeValidator timeValidator;

    public ResponseMessage<LessonProgramResponse> saveLessonProgram(
            @Valid LessonProgramRequest lessonProgramRequest) {

//        get lessons from DB
        Set<Lesson> lessons = lessonService.getAllByIdSet(lessonProgramRequest.getLessonIdList());
//        get education term from DB
        EducationTerm educationTerm = educationTermService.isEducationTermExist(lessonProgramRequest.getEducationTermId());
//        validate start + end time
        timeValidator.checkStartIsBeforeStop(
                lessonProgramRequest.getStartTime(), lessonProgramRequest.getStopTime());
//        mapping
        return null;
    }
}
