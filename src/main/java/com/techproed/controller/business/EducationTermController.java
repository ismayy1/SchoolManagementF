package com.techproed.controller.business;

import com.techproed.payload.requests.business.EducationTermRequest;
import com.techproed.payload.response.business.EducationTermResponse;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.service.business.EducationTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/educationTerm")
@RequiredArgsConstructor
public class EducationTermController {

    private final EducationTermService educationTermService;

    @PreAuthorize("hasAnyAuthority('Admin', 'Dean')")
    @PostMapping("/save")
    public ResponseMessage<EducationTermResponse> save(
            @Valid @RequestBody EducationTermRequest educationTermRequest) {

        return educationTermService.save(educationTermRequest);
    }
}
