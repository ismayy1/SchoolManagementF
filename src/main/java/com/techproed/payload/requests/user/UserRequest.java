package com.techproed.payload.requests.user;

import com.techproed.payload.requests.abstracts.BaseUserRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class UserRequest extends BaseUserRequest {
}
