package com.techproed.service.business;

import com.techproed.exception.BadRequestException;
import com.techproed.exception.ConflictException;
import com.techproed.payload.messages.ErrorMessages;
import com.techproed.payload.requests.business.EducationTermRequest;
import com.techproed.payload.response.business.EducationTermResponse;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.repository.business.EducationTermRepository;
import jdk.jpackage.internal.ConfigException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class EducationTermService {

    private final EducationTermRepository educationTermRepository;

    public ResponseMessage<EducationTermResponse> save(@Valid EducationTermRequest educationTermRequest) {

//        Validation
        validateEducationTermDates(educationTermRequest);

//        TODO:
//         Write mappers DTO->Entity + Entity->DTO
    }

    private void validateEducationTermDates(EducationTermRequest educationTermRequest) {
//        Validate Request By regis/start/stop
        validationEducationTermDatesForRequest(educationTermRequest);
//        Only one Education Term can exist in a year
        if (educationTermRepository.existsByTermAndYear(
                educationTermRequest.getTerm(),
                educationTermRequest.getStartDate().getYear())) {

            throw new ConflictException(ErrorMessages.EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR_MESSAGE);
        }

//        Validate not to have any conflict with other education terms
        educationTermRepository.findByYear(educationTermRequest.getStartDate().getYear())
                .forEach(educationTerm -> {
                    if (educationTerm.getStartDate().isAfter(educationTermRequest.getEndDate())
                        || educationTerm.getEndDate().isBefore(educationTermRequest.getStartDate()) {

                        throw new BadRequestException(ErrorMessages.EDUCATION_TERM_CONFLICT_MESSAGE);
                    }
                });

//                                                            2025-09-05
//                    ------------------------- 2025-06-01 -- 2025-07-30 -------------------------
    }

    private void validationEducationTermDatesForRequest(EducationTermRequest educationTermRequest) {
//        req<start>
        if (educationTermRequest.getLastRegistrationDate().isAfter(educationTermRequest.getStartDate())) {
            throw new ConflictException(ErrorMessages.EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE);
        }

//        end<start>
        if (educationTermRequest.getEndDate().isBefore(educationTermRequest.getStartDate())) {
            throw new ConflictException(ErrorMessages.EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE);
        }
    }
}
