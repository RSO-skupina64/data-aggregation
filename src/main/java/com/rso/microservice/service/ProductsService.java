package com.rso.microservice.service;


import com.rso.microservice.entity.Product;
import com.rso.microservice.repository.ProductsRepository;
import com.rso.microservice.vao.ProductsListVAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {

    final ProductsRepository productsRepository;

    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public ProductsListVAO getAllProducts() {
        List<Product> products = productsRepository.findAll();

        return new ProductsListVAO(products.size(), products);
    }

    public Product createProduct(Product product) {
        return productsRepository.save(product);
    }

}
