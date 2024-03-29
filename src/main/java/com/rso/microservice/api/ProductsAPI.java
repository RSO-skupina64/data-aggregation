package com.rso.microservice.api;

import com.rso.microservice.api.dto.ErrorDto;
import com.rso.microservice.api.dto.products.ProductDetailsDto;
import com.rso.microservice.api.dto.products.ProductsArrayResponseDto;
import com.rso.microservice.api.mapper.ProductsMapper;
import com.rso.microservice.service.ProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Tag(name = "Products")
public class ProductsAPI {

    final ProductsService productsService;

    final ProductsMapper productsMapper;

    @Autowired

    public ProductsAPI(ProductsService productsService, ProductsMapper productsMapper) {
        this.productsService = productsService;
        this.productsMapper = productsMapper;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lists products",
            description = "List products given optional paging parameters")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of products",
                    content = @Content(schema = @Schema(implementation = ProductsArrayResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<ProductsArrayResponseDto> getProducts(@RequestParam(required = false) Integer offset,
                                                                @RequestParam(required = false) Integer limit) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productsMapper.toModelProductDto(productsService.getAllProducts(offset, limit)));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Product details",
            description = "Get product details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product details",
                    content = @Content(schema = @Schema(implementation = ProductDetailsDto.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<ProductDetailsDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productsMapper.toModelProductDetailsDto(productsService.findById(id)));
    }

}
