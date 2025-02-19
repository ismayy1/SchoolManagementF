package com.techproed.payload.mappers;

import com.techproed.entity.concretes.business.Lesson;
import com.techproed.payload.requests.business.LessonRequest;
import com.techproed.payload.response.business.LessonResponse;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper {


    public Lesson mapLessonRequestToLesson(LessonRequest lessonRequest) {
        return Lesson.builder()
                .lessonName(lessonRequest.getLessonName())
                .creditScore(lessonRequest.getCreditScore())
                .isCompulsory(lessonRequest.getIsCompulsory())
                .build();
    }

    public LessonResponse mapLessonToLessonResponse(Lesson lesson) {
        return LessonResponse.builder()
                .lessonId(lesson.getId())
                .lessonName(lesson.getLessonName())
                .creditScore(lesson.getCreditScore())
                .isCompulsory(lesson.getIsCompulsory())
                .build();
    }
}
