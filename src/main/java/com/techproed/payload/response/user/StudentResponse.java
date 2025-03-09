package com.techproed.payload.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.techproed.entity.concretes.business.LessonProgram;
import com.techproed.payload.response.abstracts.BaseUserResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentResponse extends BaseUserResponse {

    private List<LessonProgram> lessonProgramList;
    private int studentNumber;
    private String motherName;
    private String fatherName;
    private boolean isActive;
}
