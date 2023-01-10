package com.rso.microservice.api.dto.administration;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class ProductWithIdDto {

    @JsonProperty("id_product")
    @NotNull(message = "is required")
    private Long idProduct;

    @JsonProperty("name")
    private String name;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("id_product_type")
    private Long idProductType;


    @JsonProperty("concentration")
    private Double concentration;

    @JsonProperty("concentration_unit")
    private String concentrationUnit;

    @JsonProperty("image")
    private byte[] image;

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getIdProductType() {
        return idProductType;
    }

    public void setIdProductType(Long idProductType) {
        this.idProductType = idProductType;
    }

    public Double getConcentration() {
        return concentration;
    }

    public void setConcentration(Double concentration) {
        this.concentration = concentration;
    }

    public String getConcentrationUnit() {
        return concentrationUnit;
    }

    public void setConcentrationUnit(String concentrationUnit) {
        this.concentrationUnit = concentrationUnit;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
