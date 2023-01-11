package com.rso.microservice.service;

import com.google.gson.Gson;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rso.microservice.api.dto.MessageDto;
import com.rso.microservice.api.dto.favoriteproducts.FavoriteProductRequestDto;
import com.rso.microservice.api.dto.pricecalculation.CalculatePriceSpecificShopRequestDto;
import com.rso.microservice.api.dto.pricecalculation.PriceCalculationResponseDto;
import com.rso.microservice.api.dto.pricecalculation.ProductCalculationDto;
import com.rso.microservice.api.dto.pricecalculation.ShopPriceDto;
import com.rso.microservice.api.graphql.PriceCalculationGraphQLRequestBody;
import com.rso.microservice.api.graphql.PriceCalculationGraphQLResponseBody;
import com.rso.microservice.api.mapper.GraphQLMapper;
import com.rso.microservice.util.GraphQLSchemaReaderUtil;
import com.rso.microservice.util.MDCUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@Service
@RefreshScope
public class PriceCalculationService {
    private static final Logger log = LoggerFactory.getLogger(PriceCalculationService.class);

    private final PriceCalculationService priceCalculationService;

    private final GraphQLMapper graphQLMapper;


    @Value("${microservice.price-calculation.url}")
    private String priceCalculationUrl;
    @Value("${microservice.price-calculation-graphql.url}")
    private String priceCalculationGraphQLUrl;

    public PriceCalculationService(PriceCalculationService priceCalculationService, GraphQLMapper graphQLMapper) {
        this.priceCalculationService = priceCalculationService;
        this.graphQLMapper = graphQLMapper;
    }

    public ShopPriceDto calculatePriceSpecificShop(
            CalculatePriceSpecificShopRequestDto calculatePriceSpecificShopRequestDto) {
        String administratorUrlSpecificShop = String.format("%s/shop", priceCalculationUrl);
        log.info("calculatePriceSpecificShop from URL: {}", administratorUrlSpecificShop);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        ShopPriceDto response = priceCalculationService.callCalculatePriceSpecificShop(
                calculatePriceSpecificShopRequestDto, requestId, version);
        log.info("received response: {}", response);
        return response;
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerCalculatePriceSpecificShop")
    public ShopPriceDto callCalculatePriceSpecificShop(
            CalculatePriceSpecificShopRequestDto calculatePriceSpecificShopRequestDto, String requestId,
            String version) {
        String url = String.format("%s/calculate/shop", priceCalculationUrl);

        MDCUtil.putAll("Data aggregation", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(calculatePriceSpecificShopRequestDto, headers);
        ResponseEntity<ShopPriceDto> response = new RestTemplate().exchange(url, HttpMethod.POST, requestEntity,
                ShopPriceDto.class);
        return response.getBody();
    }

    public ShopPriceDto circuitBreakerCalculatePriceSpecificShop(
            CalculatePriceSpecificShopRequestDto calculatePriceSpecificShopRequestDto, String requestId,
            String version) {
        MDCUtil.putAll("Data aggregation", version, requestId);
        log.error("There was an error when calling calculatePriceSpecificShop, so circuit breaker was activated");
        return null;
    }

    public PriceCalculationResponseDto calculatePrice(List<ProductCalculationDto> priceCalculationList) {
        try {

            PriceCalculationGraphQLRequestBody requestBody = new PriceCalculationGraphQLRequestBody();

            String gson = new Gson().toJson(priceCalculationList);
            // gson maps to json object with quotes, GraphQL need without so remove them
            gson = gson.replace("\"", "");

            String query = GraphQLSchemaReaderUtil.getSchemaFromFileName("calculatePrice");

            requestBody.setQuery(query.replace("$productList", gson));

            PriceCalculationGraphQLResponseBody response = callCalculatePriceGraphQL(requestBody);

            return graphQLMapper.toModel(response);
        } catch (IOException e) {
            log.error("There was an error calling calculate price", e);
            return null;
        }
    }

    public PriceCalculationGraphQLResponseBody callCalculatePriceGraphQL(
            PriceCalculationGraphQLRequestBody requestBody) {
        WebClient webClient = WebClient.builder().build();
        return webClient.post()
                .uri(priceCalculationGraphQLUrl)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(PriceCalculationGraphQLResponseBody.class).block();
    }

}
