package com.rso.microservice.api.mapper;

import com.rso.microservice.api.dto.products.ProductDetailsDto;
import com.rso.microservice.api.dto.products.ProductDto;
import com.rso.microservice.api.dto.products.ProductsArrayResponseDto;
import com.rso.microservice.entity.Product;
import com.rso.microservice.entity.ProductType;
import com.rso.microservice.vao.ProductsListVAO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductsMapper {

    @Mapping(source = "products", target = "products")
    @Mapping(source = "count", target = "count")
    ProductsArrayResponseDto toModelProductDto(ProductsListVAO productsListVAO);

    List<ProductDto> toModelProductDto(List<Product> products);

    ProductDto toModelProductDto(Product product);

    ProductDetailsDto toModelProductDetailsDto(Product product);

    default String toModelProductDto(ProductType productType) {
        return productType.getName();
    }
}
