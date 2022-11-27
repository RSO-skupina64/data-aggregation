package com.rso.microservice.entity;

import javax.persistence.*;
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

    @Column(name = "CONCENTRATION", length = 100, nullable = false)
    private String concentration;

    @Column(name = "CONCENTRATION_UNIT", length = 100, nullable = false)
    @Enumerated(EnumType.STRING)
    private ConcentrationUnitEnum concentrationUnit;

    @ManyToOne(targetEntity = Type.class)
    private Type type;

    @OneToMany(targetEntity = UserFavoriteProduct.class, mappedBy = "product")
    private List<UserFavoriteProduct> userFavoriteProducts;

    @OneToMany(targetEntity = ShopProduct.class, mappedBy = "product")
    private List<ShopProduct> shopProducts;

    @OneToMany(targetEntity = ProductHistory.class, mappedBy = "product")
    private List<ProductHistory> productHistories;

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

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
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

    public List<ShopProduct> getShopProducts() {
        return shopProducts;
    }

    public void setShopProducts(List<ShopProduct> shopProducts) {
        this.shopProducts = shopProducts;
    }

    public List<ProductHistory> getProductHistories() {
        return productHistories;
    }

    public void setProductHistories(List<ProductHistory> productHistories) {
        this.productHistories = productHistories;
    }
}
