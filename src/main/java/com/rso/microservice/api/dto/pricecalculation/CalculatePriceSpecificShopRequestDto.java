package com.rso.microservice.api.dto.pricecalculation;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class CalculatePriceSpecificShopRequestDto {

    @JsonProperty("id_shop")
    @NotBlank(message = "is required")
    private String idShop;

    @JsonProperty("products")
    List<ProductCalculationDto> productList;

    public String getIdShop() {
        return idShop;
    }

    public void setIdShop(String idShop) {
        this.idShop = idShop;
    }

    public List<ProductCalculationDto> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductCalculationDto> productList) {
        this.productList = productList;
    }

}
