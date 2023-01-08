package com.rso.microservice.api;

import com.rso.microservice.api.dto.ErrorDto;
import com.rso.microservice.api.dto.MessageDto;
import com.rso.microservice.api.dto.administration.*;
import com.rso.microservice.service.MetricsService;
import com.rso.microservice.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(AdministrationAPI.class);

    private final ShopService shopService;
    private final MetricsService metricsService;

    public AdministrationAPI(ShopService shopService, MetricsService metricsService) {
        this.shopService = shopService;
        this.metricsService = metricsService;
    }

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
    public ResponseEntity<ShopsArrayResponseDto> fetchProductPrices(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
        log.info("fetchProductPrices: ENTRY");
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        ShopsArrayResponseDto response = shopService.getShops();
        metricsService.measureExecutionTime(start);
        log.info("fetchProductPrices: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(response);
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
    public ResponseEntity<MessageDto> fetchProductPricesSpecificShop(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
            @Valid @RequestBody PricesShopRequestDto pricesShopRequest) {
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        // todo: add code here
        metricsService.measureExecutionTime(start);
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
    public ResponseEntity<ProductBasicDetailsDto> createProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                                @Valid @RequestBody ProductCreateRequestDto productCreateRequest) {
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        // todo: add code here
        metricsService.measureExecutionTime(start);
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
    public ResponseEntity<MessageDto> deleteProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                    @Valid @RequestBody DeleteProductDto deleteProduct) {
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        // todo: add code here
        metricsService.measureExecutionTime(start);
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
    public ResponseEntity<?> updateProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                           @Valid @RequestBody ProductBasicDetailsDto productBasicDetails) {
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        // todo: add code here
        metricsService.measureExecutionTime(start);
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
    public ResponseEntity<ProductShopHistoryWithIdDto> createProductHistory(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
            @Valid @RequestBody ProductShopHistoryDto productShopHistory) {
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        // todo: add code here
        metricsService.measureExecutionTime(start);
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
    public ResponseEntity<MessageDto> deleteProductHistory(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                           @Valid @RequestBody DeleteProductDto deleteProduct) {
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        // todo: add code here
        metricsService.measureExecutionTime(start);
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
    public ResponseEntity<?> updateProductHistory(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                  @Valid @RequestBody ProductShopHistoryWithIdDto productShopHistoryWithId) {
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        // todo: add code here
        metricsService.measureExecutionTime(start);
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
    public ResponseEntity<ShopWithIdDto> createShop(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                    @Valid @RequestBody ShopDto shop) {
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        // todo: add code here
        metricsService.measureExecutionTime(start);
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
    public ResponseEntity<MessageDto> deleteShop(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                 @Valid @RequestBody ShopIdDto shopId) {
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        // todo: add code here
        metricsService.measureExecutionTime(start);
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
    public ResponseEntity<?> updateShop(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                        @Valid @RequestBody ShopWithIdDto shopWithId) {
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        // todo: add code here
        metricsService.measureExecutionTime(start);
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
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        // todo: add code here
        metricsService.measureExecutionTime(start);
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
    public ResponseEntity<MessageDto> deleteUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                 @Valid @RequestBody UserIdDto userId) {
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        // todo: add code here
        metricsService.measureExecutionTime(start);
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
    public ResponseEntity<?> updateUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                        @Valid @RequestBody UserDto user) {
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        // todo: add code here
        metricsService.measureExecutionTime(start);
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
    public ResponseEntity<ProductTypeWithIdDto> createProductType(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                                  @Valid @RequestBody ProductTypeDto productType) {
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        // todo: add code here
        metricsService.measureExecutionTime(start);
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
    public ResponseEntity<MessageDto> deleteProductType(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                        @Valid @RequestBody ProductTypeIdDto productTypeId) {
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        // todo: add code here
        metricsService.measureExecutionTime(start);
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
    public ResponseEntity<?> updateProductType(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                               @Valid @RequestBody ProductTypeWithIdDto productTypeWithId) {
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        // todo: add code here
        metricsService.measureExecutionTime(start);
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
    public ResponseEntity<RoleWithIdDto> createRole(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                    @Valid @RequestBody RoleDto role) {
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        // todo: add code here
        metricsService.measureExecutionTime(start);
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
    public ResponseEntity<MessageDto> deleteProductType(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                        @Valid @RequestBody RoleIdDto roleId) {
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        // todo: add code here
        metricsService.measureExecutionTime(start);
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
    public ResponseEntity<?> updateProductType(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                               @Valid @RequestBody RoleWithIdDto roleWithId) {
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        // todo: add code here
        metricsService.measureExecutionTime(start);
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }


}
