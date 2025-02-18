package com.techproed.service.business;

import com.techproed.exception.ConflictException;
import com.techproed.payload.messages.ErrorMessages;
import com.techproed.payload.requests.business.LessonRequest;
import com.techproed.payload.response.business.LessonResponse;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.repository.business.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class LessonService {


    private final LessonRepository lessonRepository;

    public ResponseMessage<LessonResponse> saveLesson(@Valid LessonRequest lessonRequest) {

//        Validate - lesson name must be unique
        isLessonExistByName(lessonRequest.getLessonName());
    }


    private void isLessonExistByName(String lessonName) {
        if (lessonRepository.findByLessonNameEqualsIgnoreCase(lessonName).isPresent()) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_CREATED_LESSON_MESSAGE, lessonName));
        }
    }
}
