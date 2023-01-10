package com.rso.microservice.api;

import com.rso.microservice.api.dto.ErrorDto;
import com.rso.microservice.api.dto.MessageDto;
import com.rso.microservice.api.dto.administration.*;
import com.rso.microservice.service.AdministratorService;
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

    private final AdministratorService administratorService;
    private final ShopService shopService;
    private final MetricsService metricsService;

    public AdministrationAPI(AdministratorService administratorService, ShopService shopService,
                             MetricsService metricsService) {
        this.administratorService = administratorService;
        this.shopService = shopService;
        this.metricsService = metricsService;
    }

    @GetMapping(value = "/prices", produces = MediaType.APPLICATION_JSON_VALUE)
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
    public ResponseEntity<MessageDto> fetchProductPrices(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
            @RequestParam(required = false, defaultValue = "false") boolean fetchPictures) {
        log.info("fetchProductPrices: ENTRY");
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        String response = administratorService.fetchProductPrices(jwt, fetchPictures);
        metricsService.measureExecutionTime(start);
        log.info("fetchProductPrices: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(response));
    }

    @GetMapping(value = "/prices/shop/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt, @PathVariable String id,
            @RequestParam(required = false, defaultValue = "false") boolean fetchPictures) {
        log.info("fetchProductPricesSpecificShop: ENTRY");
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        String response = administratorService.fetchProductPricesSpecificShop(jwt, id, fetchPictures);
        metricsService.measureExecutionTime(start);
        log.info("fetchProductPricesSpecificShop: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(response));
    }

    @PostMapping(value = "/product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Creates new product",
            description = "Creates new product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Create new product",
                    content = @Content(schema = @Schema(implementation = ProductWithIdDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<ProductWithIdDto> createProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                          @Valid @RequestBody ProductDto product) {
        log.info("createProduct: ENTRY");
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        ProductWithIdDto productWithId = administratorService.createProduct(jwt, product);
        metricsService.measureExecutionTime(start);
        log.info("createProduct: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(productWithId);
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
                                                    @Valid @RequestBody ProductIdDto productId) {
        log.info("deleteProduct: ENTRY");
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        String response = administratorService.deleteProduct(jwt, productId);
        metricsService.measureExecutionTime(start);
        log.info("deleteProduct: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(response));
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
                                           @Valid @RequestBody ProductWithIdDto productWithId) {
        log.info("updateProduct: ENTRY");
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        String response = administratorService.updateProduct(jwt, productWithId);
        metricsService.measureExecutionTime(start);
        log.info("updateProduct: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(response));
    }

    @PostMapping(value = "/product-shop/history", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Creates new product shop history",
            description = "Creates new product shop history")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Create new product history",
                    content = @Content(schema = @Schema(implementation = ProductShopHistoryWithIdDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<ProductShopHistoryWithIdDto> createProductShopHistory(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
            @Valid @RequestBody ProductShopHistoryDto productShopHistory) {
        log.info("createProductShopHistory: ENTRY");
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        ProductShopHistoryWithIdDto productShopHistoryWithId = administratorService.createProductShopHistory(jwt,
                productShopHistory);
        metricsService.measureExecutionTime(start);
        log.info("createProductShopHistory: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(productShopHistoryWithId);
    }

    @DeleteMapping(value = "/product-shop/history", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Deletes product shop history",
            description = "Deletes product shop history")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delete product",
                    content = @Content(schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<MessageDto> deleteProductShopHistory(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                               @Valid @RequestBody ProductIdDto productId) {
        log.info("deleteProductShopHistory: ENTRY");
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        String response = administratorService.deleteProductShopHistory(jwt, productId);
        metricsService.measureExecutionTime(start);
        log.info("deleteProductShopHistory: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(response));
    }

    @PutMapping(value = "/product-shop/history", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Updates product shop history",
            description = "Updates product shop history")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update successful"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<?> updateProductShopHistory(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                      @Valid @RequestBody ProductShopHistoryWithIdDto productShopHistoryWithId) {
        log.info("updateProductHistory: ENTRY");
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        String response = administratorService.updateProductShopHistory(jwt, productShopHistoryWithId);
        metricsService.measureExecutionTime(start);
        log.info("updateProductHistory: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(response));
    }

    @PostMapping(value = "/product-type", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Creates new product type",
            description = "Creates new product type")
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
        log.info("createProductType: ENTRY");
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        ProductTypeWithIdDto productTypeWithId = administratorService.createProductType(jwt, productType);
        metricsService.measureExecutionTime(start);
        log.info("createProductType: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(productTypeWithId);
    }

    @DeleteMapping(value = "/product-type", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Deletes product type",
            description = "Deletes product type")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delete product type",
                    content = @Content(schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<MessageDto> deleteProductType(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                        @Valid @RequestBody ProductTypeIdDto productTypeId) {
        log.info("deleteProductType: ENTRY");
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        String response = administratorService.deleteProductType(jwt, productTypeId);
        metricsService.measureExecutionTime(start);
        log.info("deleteProductType: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(response));
    }

    @PutMapping(value = "/product-type", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Updates product type",
            description = "Updates product type")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update successful"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<?> updateProductType(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                               @Valid @RequestBody ProductTypeWithIdDto productTypeWithId) {
        log.info("updateProductType: ENTRY");
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        String response = administratorService.updateProductType(jwt, productTypeWithId);
        metricsService.measureExecutionTime(start);
        log.info("updateProductType: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(response));
    }

    @PostMapping(value = "/role", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Creates new user role",
            description = "Creates new user role")
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
        log.info("createRole: ENTRY");
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        RoleWithIdDto roleWithId = administratorService.createRole(jwt, role);
        metricsService.measureExecutionTime(start);
        log.info("createRole: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(roleWithId);
    }

    @DeleteMapping(value = "/role", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Deletes role",
            description = "Deletes role")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delete product type",
                    content = @Content(schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<MessageDto> deleteRole(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                 @Valid @RequestBody RoleIdDto roleId) {
        log.info("deleteRole: ENTRY");
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        String response = administratorService.deleteRole(jwt, roleId);
        metricsService.measureExecutionTime(start);
        log.info("deleteRole: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(response));
    }

    @PutMapping(value = "/role", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Updates role",
            description = "Updates role")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update successful"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<?> updateRole(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                        @Valid @RequestBody RoleWithIdDto roleWithId) {
        log.info("updateRole: ENTRY");
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        String response = administratorService.updateRole(jwt, roleWithId);
        metricsService.measureExecutionTime(start);
        log.info("updateRole: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(response));
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
        log.info("createShop: ENTRY");
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
//        ShopWithIdDto shopWithId = administratorService.createShop(jwt, shop);
        metricsService.measureExecutionTime(start);
        log.info("createShop: EXIT");
//        return ResponseEntity.status(HttpStatus.OK).body(shopWithId);
        return null;
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
        log.info("deleteShop: ENTRY");
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
//        String response = administratorService.deleteShop(jwt, shopId);
        metricsService.measureExecutionTime(start);
        log.info("deleteShop: EXIT");
//        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(response));
        return null;
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
        log.info("updateShop: ENTRY");
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
//        String response = administratorService.updateShop(jwt, shopWithId);
        metricsService.measureExecutionTime(start);
        log.info("updateShop: EXIT");
//        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(response));
        return null;
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
        log.info("getShops: ENTRY");
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
        ShopsArrayResponseDto response = shopService.getShops();
        metricsService.measureExecutionTime(start);
        log.info("getShops: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Deletes user",
            description = "Deletes user")
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
        log.info("deleteUser: ENTRY");
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
//        String response = administratorService.deleteUser(jwt, userId);
        metricsService.measureExecutionTime(start);
        log.info("deleteUser: EXIT");
//        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(response));
        return null;
    }

    @PutMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Updates user",
            description = "Updates user")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update successful"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<?> updateUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                        @Valid @RequestBody UserDto user) {
        log.info("updateUser: ENTRY");
        long start = System.currentTimeMillis();
        metricsService.increaseRequestCounterAndLogDate();
//        String response = administratorService.updateUser(jwt, user);
        metricsService.measureExecutionTime(start);
        log.info("updateUser: EXIT");
//        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(response));
        return null;
    }

}
