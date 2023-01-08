package com.rso.microservice.api.dto.administration;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserDto {

    @JsonProperty("email")
    @NotBlank(message = "is required.")
    @Email
    private String email;

    @JsonProperty("name")
    @NotBlank(message = "is required.")
    private String name;

    @JsonProperty("last_name")
    @NotBlank(message = "is required.")
    private String lastName;

    @JsonProperty("username")
    @NotBlank(message = "is required.")
    @Length(min = 5, max = 20)
    private String username;

    @JsonProperty("password")
    @NotBlank(message = "is required.")
    @Length(min = 5, max = 20)
    private String password;

}
