package com.rso.microservice.api.graphql;

import java.math.BigDecimal;
import java.util.List;

public class ShopPriceGraphQL {

    private Long idShop;
    private String shopName;
    private BigDecimal priceTotalEUR;
    private List<ProductPriceGraphQL> productPrices;

    public Long getIdShop() {
        return idShop;
    }

    public void setIdShop(Long idShop) {
        this.idShop = idShop;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public BigDecimal getPriceTotalEUR() {
        return priceTotalEUR;
    }

    public void setPriceTotalEUR(BigDecimal priceTotalEUR) {
        this.priceTotalEUR = priceTotalEUR;
    }

    public List<ProductPriceGraphQL> getProductPrices() {
        return productPrices;
    }

    public void setProductPrices(List<ProductPriceGraphQL> productPriceGraphQLS) {
        this.productPrices = productPriceGraphQLS;
    }
}
