package com.rso.microservice.api.dto.pricecalculation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductPriceDto {

    @JsonProperty("price_EUR")
    private Double priceEUR;

    @JsonProperty("id_product")
    private Long id;

    @JsonProperty("product_name")
    private String name;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("message")
    private String message;

    public Double getPriceEUR() {
        return priceEUR;
    }

    public void setPriceEUR(Double priceEUR) {
        this.priceEUR = priceEUR;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
