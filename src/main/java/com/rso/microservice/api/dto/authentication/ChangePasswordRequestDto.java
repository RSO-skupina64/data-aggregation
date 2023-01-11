package com.rso.microservice.api.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class ChangePasswordRequestDto {

    @JsonProperty("new_password")
    @NotBlank(message = "is required.")
    @Length(min = 7, max = 20)
    private String newPassword;

    @JsonProperty("repeat_password")
    @NotBlank(message = "is required.")
    @Length(min = 7, max = 20)
    private String repeatPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
