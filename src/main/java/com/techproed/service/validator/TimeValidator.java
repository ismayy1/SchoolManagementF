package com.techproed.service.validator;

import com.techproed.exception.BadRequestException;
import com.techproed.payload.messages.ErrorMessages;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class TimeValidator {

//    validate if start time is before stop time
    public void checkStartIsBeforeStop(LocalTime start, LocalTime stop) {
        if (start.isBefore(stop) || start.equals(stop)) {
            throw new BadRequestException(ErrorMessages.TIME_NOT_VALID_MESSAGE);
        }
    }
}
