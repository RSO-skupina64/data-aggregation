package com.rso.microservice.api.dto.pricecalculation;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ProductCalculationDto {

    @JsonProperty("id_product")
    @NotNull(message = "is required")
    @Min(1)
    private long id;

    @JsonProperty("quantity")
    @NotNull(message = "is required")
    @Min(1)
    private Integer quantity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
