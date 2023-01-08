package com.rso.microservice.api;

import com.rso.microservice.api.dto.ErrorDto;
import com.rso.microservice.api.dto.MessageDto;
import com.rso.microservice.api.dto.administration.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/administration")
@Tag(name = "Administration")
public class AdministrationAPI {

    @PostMapping(value = "/prices", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Fetches prices for all shops",
            description = "Fetches prices for all shops")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prices",
                    content = @Content(schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<MessageDto> fetchProductPrices(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
        // todo: add code here
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PostMapping(value = "/prices/shop", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Fetches prices for specific shop",
            description = "Fetches prices for specific shop")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prices",
                    content = @Content(schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<MessageDto> fetchProductPricesSpecificShop(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt, @Valid @RequestBody PricesShopRequestDto pricesShopRequest) {
        // todo: add code here
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PostMapping(value = "/product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Creates new product",
            description = "Creates new product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Create new product",
                    content = @Content(schema = @Schema(implementation = ProductBasicDetailsDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<ProductBasicDetailsDto> createProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt, @Valid @RequestBody ProductCreateRequestDto productCreateRequest) {
        // todo: add code here
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @DeleteMapping(value = "/product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Deletes product",
            description = "Deletes product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delete product",
                    content = @Content(schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<MessageDto> deleteProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt, @Valid @RequestBody DeleteProductDto deleteProduct) {
        // todo: add code here
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PutMapping(value = "/product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Updates product",
            description = "Updates product")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update successful"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<?> updateProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt, @Valid @RequestBody ProductBasicDetailsDto productBasicDetails) {
        // todo: add code here
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PostMapping(value = "/product-shop/history", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Creates new product history",
            description = "Creates new product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Create new product history",
                    content = @Content(schema = @Schema(implementation = ProductShopHistoryWithIdDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<ProductShopHistoryWithIdDto> createProductHistory(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt, @Valid @RequestBody ProductShopHistoryDto productShopHistory) {
        // todo: add code here
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @DeleteMapping(value = "/product-shop/history", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Deletes product",
            description = "Deletes product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delete product",
                    content = @Content(schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<MessageDto> deleteProductHistory(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt, @Valid @RequestBody DeleteProductDto deleteProduct) {
        // todo: add code here
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PutMapping(value = "/product-shop/history", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Updates product",
            description = "Updates product")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update successful"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<?> updateProductHistory(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt, @Valid @RequestBody ProductShopHistoryWithIdDto productShopHistoryWithId) {
        // todo: add code here
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PostMapping(value = "/shop", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Creates new Shop",
            description = "Creates new Shop")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Create new shop",
                    content = @Content(schema = @Schema(implementation = ShopWithIdDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<ShopWithIdDto> createShop(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt, @Valid @RequestBody ShopDto shop) {
        // todo: add code here
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @DeleteMapping(value = "/shop", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Deletes shop",
            description = "Deletes shop")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delete shop",
                    content = @Content(schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<MessageDto> deleteShop(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt, @Valid @RequestBody ShopIdDto shopId) {
        // todo: add code here
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PutMapping(value = "/shop", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Updates shop",
            description = "Updates shop")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update successful"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<?> updateShop(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt, @Valid @RequestBody ShopWithIdDto shopWithId) {
        // todo: add code here
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping(value = "/multi", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all shops",
            description = "Get all shops")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of shops",
                    content = @Content(schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<ShopsArrayResponseDto> getShops() {
        // todo: add code here
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @DeleteMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Deletes User",
            description = "Deletes User")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delete shop",
                    content = @Content(schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<MessageDto> deleteUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt, @Valid @RequestBody UserIdDto userId) {
        // todo: add code here
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PutMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Updates User",
            description = "Updates User")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update successful"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<?> updateUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt, @Valid @RequestBody UserDto user) {
        // todo: add code here
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PostMapping(value = "/product-type", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Creates new Type",
            description = "Creates new Type")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Creation successful",
                    content = @Content(schema = @Schema(implementation = ProductTypeWithIdDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<ProductTypeWithIdDto> createProductType(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt, @Valid @RequestBody ProductTypeDto productType) {
        // todo: add code here
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @DeleteMapping(value = "/product-type", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Deletes Type",
            description = "Deletes Type")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delete Type",
                    content = @Content(schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<MessageDto> deleteProductType(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt, @Valid @RequestBody ProductTypeIdDto productTypeId) {
        // todo: add code here
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PutMapping(value = "/product-type", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Updates Type",
            description = "Updates Type")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update successful"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<?> updateProductType(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt, @Valid @RequestBody ProductTypeWithIdDto productTypeWithId) {
        // todo: add code here
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PostMapping(value = "/role", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Creates new User role",
            description = "Creates new User role")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Creation successful",
                    content = @Content(schema = @Schema(implementation = RoleWithIdDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<RoleWithIdDto> createRole(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt, @Valid @RequestBody RoleDto role) {
        // todo: add code here
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @DeleteMapping(value = "/role", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Deletes Role",
            description = "Deletes Role")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delete Type",
                    content = @Content(schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<MessageDto> deleteProductType(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt, @Valid @RequestBody RoleIdDto roleId) {
        // todo: add code here
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PutMapping(value = "/role", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Updates Role",
            description = "Updates Role")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update successful"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<?> updateProductType(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt, @Valid @RequestBody RoleWithIdDto roleWithId) {
        // todo: add code here
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }


}
