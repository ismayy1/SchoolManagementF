package com.techproed.payload.requests.user;

import com.techproed.payload.requests.abstracts.BaseUserRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class TeacherRequest extends BaseUserRequest {

    @NotNull(message = "Please select lesson program")
    private List<Long> lessonProgramList;

    @NotNull(message = "Please select isAdvisor teacher")
    private Boolean isAdvisoryTeacher;
}
