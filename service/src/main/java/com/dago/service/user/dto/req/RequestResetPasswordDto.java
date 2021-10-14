package com.dago.service.user.dto.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter @Setter
public class RequestResetPasswordDto {
    @Email
    private String email;
}