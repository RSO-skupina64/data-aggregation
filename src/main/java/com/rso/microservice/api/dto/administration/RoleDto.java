package com.rso.microservice.api.dto.administration;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class RoleDto {

    @JsonProperty("name")
    @NotBlank(message = "is required")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
