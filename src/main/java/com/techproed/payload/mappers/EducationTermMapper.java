package com.techproed.payload.mappers;

import com.techproed.entity.concretes.business.EducationTerm;
import com.techproed.payload.requests.business.EducationTermRequest;
import com.techproed.payload.response.business.EducationTermResponse;
import org.springframework.stereotype.Component;

@Component
public class EducationTermMapper {

    /**
     *
     * @param educationTermRequest DTO from Postman or FE
     * @return  EducationTerm Entity
     */
    public EducationTerm mapEducationTermRequestToEducationTerm(EducationTermRequest educationTermRequest){
        return EducationTerm.builder()
                .term(educationTermRequest.getTerm())
                .startDate(educationTermRequest.getStartDate())
                .endDate(educationTermRequest.getEndDate())
                .lastRegistrationDate(educationTermRequest.getLastRegistrationDate())
                .build();
    }

    /**
     *
     * @param educationTerm Entity fetched from DB
     * @return EducationTermResponse DTO
     */
    public EducationTermResponse mapEducationTermToEducationTermResponse(EducationTerm educationTerm){
        return EducationTermResponse.builder()
                .id(educationTerm.getId())
                .term(educationTerm.getTerm())
                .startDate(educationTerm.getStartDate())
                .endDate(educationTerm.getEndDate())
                .lastRegistrationDate(educationTerm.getLastRegistrationDate())
                .build();
    }
}