package com.rso.microservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rso.microservice.api.dto.MessageDto;
import com.rso.microservice.api.dto.favoriteproducts.FavoriteProductRequestDto;
import com.rso.microservice.api.dto.favoriteproducts.FavoriteProductsArrayResponseDto;
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

import java.util.ArrayList;

@Service
@RefreshScope
public class FavoriteProductsService {
    private static final Logger log = LoggerFactory.getLogger(FavoriteProductsService.class);

    private final FavoriteProductsService favoriteProductsService;

    @Value("${microservice.favorite.products.url}")
    private String favoriteProductsUrl;

    public FavoriteProductsService(FavoriteProductsService favoriteProductsService) {
        this.favoriteProductsService = favoriteProductsService;
    }

    public String addFavoriteProduct(FavoriteProductRequestDto favoriteProductRequest, String jwt) {
        log.info("addFavoriteProduct from URL: {}", favoriteProductsUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        MessageDto response = favoriteProductsService.callAddFavoriteProduct(favoriteProductRequest, jwt, requestId,
                version);
        log.info("received response: {}", response.getMessage());
        return response.getMessage();
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerAddFavoriteProduct")
    public MessageDto callAddFavoriteProduct(FavoriteProductRequestDto favoriteProductRequest, String jwt,
                                             String requestId, String version) {
        MDCUtil.putAll("Data aggregation", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(favoriteProductRequest, headers);
        ResponseEntity<MessageDto> response = new RestTemplate().exchange(favoriteProductsUrl, HttpMethod.POST,
                requestEntity, MessageDto.class);
        return response.getBody();
    }

    public MessageDto circuitBreakerAddFavoriteProduct(FavoriteProductRequestDto favoriteProductRequest, String jwt,
                                                       String requestId, String version, Throwable ex) {
        MDCUtil.putAll("Data aggregation", version, requestId);
        log.error(String.valueOf(ex));
        log.error("There was an error when calling addFavoriteProduct, so circuit breaker was activated");
        return new MessageDto("Error while calling favorite products, circuit breaker method called");
    }

    public String removeFavoriteProduct(FavoriteProductRequestDto favoriteProductRequest, String jwt) {
        log.info("removeFavoriteProduct from URL: {}", favoriteProductsUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        MessageDto response = favoriteProductsService.callRemoveFavoriteProduct(favoriteProductRequest, jwt, requestId,
                version);
        log.info("received response: {}", response.getMessage());
        return response.getMessage();
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerRemoveFavoriteProduct")
    public MessageDto callRemoveFavoriteProduct(FavoriteProductRequestDto favoriteProductRequest, String jwt,
                                                String requestId, String version) {
        MDCUtil.putAll("Data aggregation", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(favoriteProductRequest, headers);
        ResponseEntity<MessageDto> response = new RestTemplate().exchange(favoriteProductsUrl, HttpMethod.DELETE,
                requestEntity, MessageDto.class);
        return response.getBody();
    }

    public MessageDto circuitBreakerRemoveFavoriteProduct(FavoriteProductRequestDto favoriteProductRequest, String jwt,
                                                          String requestId, String version) {
        MDCUtil.putAll("Data aggregation", version, requestId);
        log.error("There was an error when calling removeFavoriteProduct, so circuit breaker was activated");
        return new MessageDto("Error while calling favorite products, circuit breaker method called");
    }

    public FavoriteProductsArrayResponseDto getFavoriteProducts(String jwt) {
        log.info("getFavoriteProducts from URL: {}", favoriteProductsUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        FavoriteProductsArrayResponseDto response = favoriteProductsService.callGetFavoriteProducts(jwt, requestId,
                version);
        log.info("received response: {}", response);
        return response;
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerGetFavoriteProducts")
    public FavoriteProductsArrayResponseDto callGetFavoriteProducts(String jwt, String requestId, String version) {
        // todo zamenjaj messagedto z FavoriteProductsArrayResponseDto
        MDCUtil.putAll("Data aggregation", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<FavoriteProductsArrayResponseDto> response = new RestTemplate().exchange(favoriteProductsUrl,
                HttpMethod.GET, requestEntity, FavoriteProductsArrayResponseDto.class);
        return response.getBody();
    }

    public FavoriteProductsArrayResponseDto circuitBreakerGetFavoriteProducts(String jwt, String requestId,
                                                                              String version) {
        MDCUtil.putAll("Data aggregation", version, requestId);
        FavoriteProductsArrayResponseDto favoriteProductsArrayResponse = new FavoriteProductsArrayResponseDto();
        favoriteProductsArrayResponse.setCount(0);
        favoriteProductsArrayResponse.setProducts(new ArrayList<>());
        log.error("There was an error when calling getFavoriteProducts, so circuit breaker was activated");
        return favoriteProductsArrayResponse;
    }

}
