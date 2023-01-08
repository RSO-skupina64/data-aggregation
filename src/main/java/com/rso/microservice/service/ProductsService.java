package com.rso.microservice.service;


import com.rso.microservice.entity.Product;
import com.rso.microservice.repository.ProductRepository;
import com.rso.microservice.vao.ProductsListVAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {

    final ProductRepository productRepository;

    public ProductsService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductsListVAO getAllProducts() {
        List<Product> products = productRepository.findAll();

        return new ProductsListVAO(products.size(), products);
    }

}
