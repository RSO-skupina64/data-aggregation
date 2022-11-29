package com.rso.microservice.api.mapper;

import com.rso.microservice.api.dto.products.ProductDto;
import com.rso.microservice.api.dto.products.ProductsArrayResponseDto;
import com.rso.microservice.entity.Product;
import com.rso.microservice.entity.Type;
import com.rso.microservice.vao.ProductsListVAO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "products", target = "products")
    @Mapping(source = "count", target = "count")
    ProductsArrayResponseDto toModel(ProductsListVAO productsListVAO);

    List<ProductDto> toModel(List<Product> products);

    ProductDto toModel(Product product);

    @Mapping(source = "name", target = ".")
    String toModel(Type type);



}
