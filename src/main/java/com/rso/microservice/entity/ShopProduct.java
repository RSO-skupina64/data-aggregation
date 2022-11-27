package com.rso.microservice.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "SHOP_PRODUCT")
public class ShopProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PRICE_EUR", nullable = false)
    private BigDecimal price;

    @ManyToOne(targetEntity = Shop.class)
    private Shop shop;

    @ManyToOne(targetEntity = Product.class)
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
