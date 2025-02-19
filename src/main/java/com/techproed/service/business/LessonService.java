package com.techproed.service.business;

import com.techproed.entity.concretes.business.Lesson;
import com.techproed.exception.ConflictException;
import com.techproed.payload.mappers.LessonMapper;
import com.techproed.payload.messages.ErrorMessages;
import com.techproed.payload.messages.SuccessMessages;
import com.techproed.payload.requests.business.LessonRequest;
import com.techproed.payload.response.business.LessonResponse;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.repository.business.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class LessonService {


    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    public ResponseMessage<LessonResponse> saveLesson(@Valid LessonRequest lessonRequest) {

//        Validate - lesson name must be unique
        isLessonExistByName(lessonRequest.getLessonName());
//        map DTO to Entity
        Lesson lesson = lessonMapper.mapLessonRequestToLesson(lessonRequest);
        Lesson savedLesson = lessonRepository.save(lesson);

        return ResponseMessage.<LessonResponse>builder()
                .returnBody(lessonMapper.mapLessonToLessonRequest(savedLesson))
                .httpStatus(HttpStatus.CREATED)
                .message(SuccessMessages.LESSON_SAVE)
                .build();
    }


    private void isLessonExistByName(String lessonName) {
        if (lessonRepository.findByLessonNameEqualsIgnoreCase(lessonName).isPresent()) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_CREATED_LESSON_MESSAGE, lessonName));
        }
    }
}
