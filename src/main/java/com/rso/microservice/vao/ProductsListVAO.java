package com.rso.microservice.vao;

import com.rso.microservice.entity.Product;

import java.util.List;

public class ProductsListVAO {

    private final Integer count;
    private final List<Product> products;

    public ProductsListVAO(int count, List<Product> products) {
        this.count = count;
        this.products = products;
    }

    public Integer getCount() {
        return count;
    }

    public List<Product> getProducts() {
        return products;
    }
}
