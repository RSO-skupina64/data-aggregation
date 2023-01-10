package com.rso.microservice.service;


import com.rso.microservice.entity.Product;
import com.rso.microservice.entity.ProductType;
import com.rso.microservice.entity.Shop;
import com.rso.microservice.repository.ProductRepository;
import com.rso.microservice.vao.ProductsListVAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {

    final ProductRepository productRepository;

    public ProductsService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductsListVAO getAllProducts(Integer offset, Integer limit) {
        List<Product> products;
        if (offset == null || limit == null) {
            products = productRepository.findAll();
        } else {
            products = productRepository.findAll(offset, limit);
        }

        return new ProductsListVAO(products.size(), products);
    }

    public Product findById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        return productOptional.orElse(null);

    }

}
