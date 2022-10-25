package com.rso.microservice.api.dto.favoriteproducts;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class FavoriteProductRequestDto {

    @JsonProperty("product_id")
    @NotBlank(message = " is required.")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
