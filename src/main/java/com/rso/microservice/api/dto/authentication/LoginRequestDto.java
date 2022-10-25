package com.rso.microservice.api.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class LoginRequestDto {

    @JsonProperty("username")
    @NotBlank(message = "is required.")
    @Length(min = 5, max = 20)
    private String username;

    @JsonProperty("password")
    @NotBlank(message = "is required.")
    @Length(min = 7, max = 20)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
