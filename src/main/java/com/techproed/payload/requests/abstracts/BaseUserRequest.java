package com.techproed.payload.requests.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseUserRequest extends AbstractUserRequest {

    @NotNull(message = "Please enter your password!")
    @Size(min = 8, max = 20, message = "Your password shall be {min}-{max}")
    private String password;

    private Boolean buildIn = false;
}
