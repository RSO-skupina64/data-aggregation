package com.rso.microservice.api.dto.products;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ShopProductsDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("shop_name")
    private String shopName;

    @JsonProperty("product_history")
    List<ProductHistoryDto> productHistory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<ProductHistoryDto> getProductHistory() {
        return productHistory;
    }

    public void setProductHistory(List<ProductHistoryDto> productHistory) {
        this.productHistory = productHistory;
    }
}
