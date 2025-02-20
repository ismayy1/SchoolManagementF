package com.techproed.payload.mappers;

import com.techproed.entity.concretes.business.EducationTerm;
import com.techproed.entity.concretes.business.Lesson;
import com.techproed.entity.concretes.business.LessonProgram;
import com.techproed.payload.requests.business.LessonProgramRequest;
import com.techproed.payload.response.LessonProgramResponse;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class LessonProgramMapper {

    public LessonProgram mapLessonProgramRequestToLessonProgram(
            LessonProgramRequest lessonProgramRequest,
            Set<Lesson> lessonSet,
            EducationTerm educationTerm) {

        return LessonProgram.builder()
                .startTime(lessonProgramRequest.getStartTime())
                .stopTime(lessonProgramRequest.getStopTime())
                .day(lessonProgramRequest.getDay())
                .lessons(lessonSet)
                .educationTerm(educationTerm)
                .build();
    }

    public LessonProgramResponse mapLessonProgramToLessonProgramResponse(LessonProgram lessonProgram) {
        return LessonProgramResponse.builder()
                .day(lessonProgram.getDay())
                .educationTerm(lessonProgram.getEducationTerm())
                .startTime(lessonProgram.getStartTime())
                .stopTime(lessonProgram.getStopTime())
                .lessonName(lessonProgram.getLessons())
                .lessonProgramId(lessonProgram.getId())
                .build();
    }
}
