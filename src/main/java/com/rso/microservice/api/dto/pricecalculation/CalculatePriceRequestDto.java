package com.rso.microservice.api.dto.pricecalculation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CalculatePriceRequestDto {

    @JsonProperty("products")
    List<ProductCalculationDto> productList;

    public List<ProductCalculationDto> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductCalculationDto> productList) {
        this.productList = productList;
    }
}
