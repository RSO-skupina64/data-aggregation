package com.rso.microservice.api;

import com.rso.microservice.api.dto.ErrorDto;
import com.rso.microservice.api.dto.pricecalculation.CalculatePriceRequestDto;
import com.rso.microservice.api.dto.pricecalculation.CalculatePriceSpecificShopRequestDto;
import com.rso.microservice.api.dto.pricecalculation.PriceCalculationResponseDto;
import com.rso.microservice.api.dto.pricecalculation.ShopPriceDto;
import com.rso.microservice.service.PriceCalculationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/price")
@Tag(name = "Price Calculation")
public class PriceCalculationAPI {
    private static final Logger log = LoggerFactory.getLogger(PriceCalculationAPI.class);

    private final PriceCalculationService priceCalculationService;

    public PriceCalculationAPI(PriceCalculationService priceCalculationService) {
        this.priceCalculationService = priceCalculationService;
    }

    @PostMapping(value = "/calculate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Calculates price of products for all shops",
            description = "Calculates price of products in request for all shops")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prices",
                    content = @Content(schema = @Schema(implementation = PriceCalculationResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<PriceCalculationResponseDto> calculatePrice(@Valid @RequestBody CalculatePriceRequestDto favoriteProductRequest) {
        log.info("calculatePrice: ENTRY");
        PriceCalculationResponseDto priceCalculationResponseDto = priceCalculationService.calculatePrice(favoriteProductRequest.getProductList());
        log.info("calculatePrice: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(priceCalculationResponseDto);
    }

    @PostMapping(value = "/calculate/shop", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Calculates price of products for specific shop",
            description = "Calculates price of products in request for specific shop")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Price",
                    content = @Content(schema = @Schema(implementation = ShopPriceDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<ShopPriceDto> calculatePriceSpecificShop(@Valid @RequestBody CalculatePriceSpecificShopRequestDto calculatePriceSpecificShopRequestDto) {
        log.info("calculatePriceSpecificShop: ENTRY");
        ShopPriceDto shopPriceDto = priceCalculationService.calculatePriceSpecificShop(calculatePriceSpecificShopRequestDto);
        log.info("calculatePriceSpecificShop: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(shopPriceDto);
    }

}
