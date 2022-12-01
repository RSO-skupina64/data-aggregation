package com.rso.microservice.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", length = 100, nullable = false)
    private String name;

    @Column(name = "BRAND", length = 100, nullable = false)
    private String brand;

    @Column(name = "CONCENTRATION", precision = 10, scale = 2, nullable = false)
    private BigDecimal concentration;

    @Column(name = "CONCENTRATION_UNIT", length = 100, nullable = false)
    @Enumerated(EnumType.STRING)
    private ConcentrationUnitEnum concentrationUnit;

    @ManyToOne(targetEntity = Type.class)
    private Type type;

    @OneToMany(targetEntity = UserFavoriteProduct.class, mappedBy = "product")
    private List<UserFavoriteProduct> userFavoriteProducts;

    @OneToMany(targetEntity = ProductShop.class, mappedBy = "product")
    private List<ProductShop> productShops;

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

    public BigDecimal getConcentration() {
        return concentration;
    }

    public void setConcentration(BigDecimal concentration) {
        this.concentration = concentration;
    }

    public ConcentrationUnitEnum getConcentrationUnit() {
        return concentrationUnit;
    }

    public void setConcentrationUnit(ConcentrationUnitEnum concentrationUnit) {
        this.concentrationUnit = concentrationUnit;
    }

    public List<UserFavoriteProduct> getUserFavoriteProducts() {
        return userFavoriteProducts;
    }

    public void setUserFavoriteProducts(List<UserFavoriteProduct> userFavoriteProducts) {
        this.userFavoriteProducts = userFavoriteProducts;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<ProductShop> getShopProducts() {
        return productShops;
    }

    public void setShopProducts(List<ProductShop> productShops) {
        this.productShops = productShops;
    }
}
