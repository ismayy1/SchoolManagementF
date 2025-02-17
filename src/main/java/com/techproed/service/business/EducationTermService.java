package com.techproed.service.business;

import com.techproed.entity.concretes.business.EducationTerm;
import com.techproed.exception.BadRequestException;
import com.techproed.exception.ConflictException;
import com.techproed.exception.ResourceNotFoundException;
import com.techproed.payload.mappers.EducationTermMapper;
import com.techproed.payload.messages.ErrorMessages;
import com.techproed.payload.messages.SuccessMessages;
import com.techproed.payload.requests.business.EducationTermRequest;
import com.techproed.payload.response.business.EducationTermResponse;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.repository.business.EducationTermRepository;
import com.techproed.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationTermService {

    private final EducationTermRepository educationTermRepository;
    private final EducationTermMapper educationTermMapper;
    private final PageableHelper pageableHelper;

    public ResponseMessage<EducationTermResponse> save(@Valid EducationTermRequest educationTermRequest) {

//        Validation
        validateEducationTermDates(educationTermRequest);

//        TODO:
//         Write mappers DTO->Entity + Entity->DTO
        EducationTerm educationTerm = educationTermMapper.mapEducationTermRequestToEducationTerm(educationTermRequest);
        EducationTerm saveEducationTerm = educationTermRepository.save(educationTerm);

        return ResponseMessage.<EducationTermResponse>builder()
                .message(SuccessMessages.EDUCATION_TERM_SAVE)
                .returnBody(educationTermMapper.mapEducationTermToEducationTermResponse(saveEducationTerm))
                .httpStatus(HttpStatus.CREATED)
                .build();
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
                        || educationTerm.getEndDate().isBefore(educationTermRequest.getStartDate())) {

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

    public ResponseMessage<EducationTermResponse> updateEducationTerms(
            @Valid EducationTermRequest educationTermRequest, Long educationTermId) {

//        Check if educationTerm exist
        isEducationTermExist(educationTermId);
//        validate dates
        validationEducationTermDatesForRequest(educationTermRequest);
//        mapping
        EducationTerm term = educationTermMapper.mapEducationTermRequestToEducationTerm(educationTermRequest);
        term.setId(educationTermId);
//        save to DB
        EducationTerm savedEducationTerm = educationTermRepository.save(term);
//        return by mapping it to DTO
        return ResponseMessage.<EducationTermResponse>builder()
                .message(SuccessMessages.EDUCATION_TERM_UPDATE)
                .returnBody(educationTermMapper.mapEducationTermToEducationTermResponse(savedEducationTerm))
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public EducationTerm isEducationTermExist(Long educationTermId) {
        return educationTermRepository.findById(educationTermId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.EDUCATION_TERM_NOT_FOUND_MESSAGE));
    }

    public Page<EducationTermResponse> getByPage(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageable(page, size, sort, type);
//        fetch paginated and sorted data from DB
        Page<EducationTerm> educationTerms = educationTermRepository.findAll(pageable);
//        use mapper
        return educationTerms.map(educationTermMapper::mapEducationTermToEducationTermResponse);
    }

    public ResponseMessage deleteById(Long educationTermId) {

        isEducationTermExist(educationTermId);
        return ResponseMessage.builder()
                .message(SuccessMessages.EDUCATION_TERM_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
