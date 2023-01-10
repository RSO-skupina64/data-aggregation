package com.rso.microservice.api.dto.administration;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class ProductTypeIdDto {

    @JsonProperty("id_product_type")
    @NotBlank(message = "is required")
    private String idProductType;

    public String getIdProductType() {
        return idProductType;
    }

    public void setIdProductType(String idProductType) {
        this.idProductType = idProductType;
    }
}
