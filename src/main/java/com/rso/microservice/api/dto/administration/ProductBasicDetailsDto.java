package com.rso.microservice.api.dto.administration;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class ProductBasicDetailsDto {

    @JsonProperty("id")
    @NotBlank(message = "is required")
    private Long id;

    @JsonProperty("name")
    @NotBlank(message = "is required")
    private String name;

    @JsonProperty("brand")
    @NotBlank(message = "is required")
    private String brand;

    @JsonProperty("type")
    @NotBlank(message = "is required")
    private String type;

    @JsonProperty("concentration")
    @NotBlank(message = "is required")
    private Double concentration;

    @JsonProperty("concentration_unit")
    @NotBlank(message = "is required")
    private String concentrationUnit;

    @JsonProperty("image")
    private String image;

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
