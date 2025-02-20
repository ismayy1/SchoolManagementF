package com.techproed.service.business;

import com.techproed.entity.concretes.business.EducationTerm;
import com.techproed.entity.concretes.business.Lesson;
import com.techproed.entity.concretes.business.LessonProgram;
import com.techproed.payload.mappers.LessonProgramMapper;
import com.techproed.payload.messages.SuccessMessages;
import com.techproed.payload.requests.business.LessonProgramRequest;
import com.techproed.payload.response.LessonProgramResponse;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.repository.business.LessonProgramRepository;
import com.techproed.service.validator.TimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonProgramService {

    private final LessonProgramRepository lessonProgramRepository;
    private final LessonService lessonService;
    private final EducationTermService educationTermService;
    private final TimeValidator timeValidator;
    private final LessonProgramMapper lessonProgramMapper;

    public ResponseMessage<LessonProgramResponse> saveLessonProgram(
            @Valid LessonProgramRequest lessonProgramRequest) {

//        get lessons from DB
        List<Lesson> lessons = lessonService.getAllByIdSet(lessonProgramRequest.getLessonIdList());
//        get education term from DB
        EducationTerm educationTerm = educationTermService.isEducationTermExist(lessonProgramRequest.getEducationTermId());
//        validate start + end time
        timeValidator.checkStartIsBeforeStop(
                lessonProgramRequest.getStartTime(), lessonProgramRequest.getStopTime());
//        mapping
        LessonProgram lessonProgramToSave = lessonProgramMapper.mapLessonProgramRequestToLessonProgram(
                lessonProgramRequest, lessons, educationTerm);

        LessonProgram savedLessonProgram = lessonProgramRepository.save(lessonProgramToSave);

        return ResponseMessage.<LessonProgramResponse>builder()
                .returnBody(lessonProgramMapper.mapLessonProgramToLessonProgramResponse(savedLessonProgram))
                .httpStatus(HttpStatus.CREATED)
                .message(SuccessMessages.LESSON_PROGRAM_SAVE)
                .build();
    }

    public List<LessonProgramResponse> getAllUnassigned() {

        return lessonProgramRepository.findByUsers_IdNull()
                .stream()
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toList());
    }

    public List<LessonProgramResponse> getAllAssigned() {

        return lessonProgramRepository.findByUsers_IdNotNull()
                .stream()
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toList());
    }
}
