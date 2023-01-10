package com.rso.microservice.api;

import com.rso.microservice.api.dto.ErrorDto;
import com.rso.microservice.api.dto.MessageDto;
import com.rso.microservice.api.dto.favoriteproducts.FavoriteProductRequestDto;
import com.rso.microservice.api.dto.favoriteproducts.FavoriteProductsArrayResponseDto;
import com.rso.microservice.service.FavoriteProductsService;
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
@RequestMapping("/favorite")
@Tag(name = "Favorite Products")
public class FavoriteProductsAPI {
    private static final Logger log = LoggerFactory.getLogger(FavoriteProductsAPI.class);

    private final FavoriteProductsService favoriteProductsService;

    public FavoriteProductsAPI(FavoriteProductsService favoriteProductsService) {
        this.favoriteProductsService = favoriteProductsService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add product to favorites",
            description = "Add a product to User's favorites")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of products",
                    content = @Content(schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<MessageDto> addFavoriteProduct(
            @Valid @RequestBody FavoriteProductRequestDto favoriteProductRequest,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
        log.info("addFavoriteProduct: ENTRY");
        String response = favoriteProductsService.addFavoriteProduct(favoriteProductRequest, jwt);
        log.info("addFavoriteProduct: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(response));
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Remove product from favorites",
            description = "Remove product from favorites")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of products",
                    content = @Content(schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<MessageDto> removeFavoriteProduct(
            @Valid @RequestBody FavoriteProductRequestDto favoriteProductRequest,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
        log.info("removeFavoriteProduct: ENTRY");
        String response = favoriteProductsService.removeFavoriteProduct(favoriteProductRequest, jwt);
        log.info("removeFavoriteProduct: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(response));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get favorite products",
            description = "Get favorite products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of products",
                    content = @Content(schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<FavoriteProductsArrayResponseDto> getFavoriteProducts(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
        log.info("getFavoriteProducts: ENTRY");
        FavoriteProductsArrayResponseDto favoriteProductsArrayResponse = favoriteProductsService.getFavoriteProducts(
                jwt);
        log.info("getFavoriteProducts: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(favoriteProductsArrayResponse);
    }

}
