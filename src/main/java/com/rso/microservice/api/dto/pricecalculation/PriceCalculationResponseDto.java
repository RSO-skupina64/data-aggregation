package com.rso.microservice.api.dto.pricecalculation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PriceCalculationResponseDto {


    @JsonProperty("shop_prices")
    private List<ShopPriceDto> shopPrices;

    public List<ShopPriceDto> getShopPrices() {
        return shopPrices;
    }

    public void setShopPrices(List<ShopPriceDto> shopPrices) {
        this.shopPrices = shopPrices;
    }
}
