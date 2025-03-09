package com.techproed.payload.response.abstracts;

import com.techproed.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseUserResponse {

    private Long id;
    private String username;
    private String name;
    private String surname;
    private LocalDate birthDay;
    private String ssn;
    private String birthPlace;
    private String phoneNumber;
    private String email;
    private Gender gender;
    private String userRole;
}
