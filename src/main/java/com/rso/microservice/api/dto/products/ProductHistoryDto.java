package com.rso.microservice.api.dto.products;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ProductHistoryDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("date")
    private LocalDateTime date;

    @JsonProperty("price_eur")
    private Double priceEUR;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getPriceEUR() {
        return priceEUR;
    }

    public void setPriceEUR(Double priceEUR) {
        this.priceEUR = priceEUR;
    }
}
