package com.dago.service.user.dto.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class ChangePasswordDto {
    @NotNull
    private String resetToken;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"
            , message = "Minimum eight characters, at least one letter and one number!")
    private String newPassword;
}