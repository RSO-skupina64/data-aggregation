package com.rso.microservice.api.dto.products;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ShopProductsDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("shop_name")
    private String shopName;

    @JsonProperty("product_shop_history")
    List<ProductShopHistoryDto> productShopHistory;

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

    public List<ProductShopHistoryDto> getProductShopHistory() {
        return productShopHistory;
    }

    public void setProductShopHistory(List<ProductShopHistoryDto> productShopHistory) {
        this.productShopHistory = productShopHistory;
    }
}
