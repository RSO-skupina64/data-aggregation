package com.rso.microservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rso.microservice.api.dto.MessageDto;
import com.rso.microservice.api.dto.administration.ProductDto;
import com.rso.microservice.api.dto.administration.ProductIdDto;
import com.rso.microservice.api.dto.administration.ProductWithIdDto;
import com.rso.microservice.api.dto.favoriteproducts.FavoriteProductRequestDto;
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
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RefreshScope
public class AdministratorService {
    private static final Logger log = LoggerFactory.getLogger(AdministratorService.class);

    private final AdministratorService administratorService;

    @Value("${microservice.administrator.url}")
    private String administratorUrl;
    @Value("${microservice.administrator.url}/prices")
    private String administratorPricesUrl;
    @Value("${microservice.administrator.url}/product")
    private String administratorProductUrl;
    @Value("${microservice.administrator.url}/product-shop/history")
    private String administratorProductShopHistoryUrl;
    @Value("${microservice.administrator.url}/product-type")
    private String administratorProductTypesUrl;
    @Value("${microservice.administrator.url}/role")
    private String administratorRoleUrl;
    @Value("${microservice.administrator.url}/shop")
    private String administratorShopUrl;
    @Value("${microservice.administrator.url}/user")
    private String administratorUserUrl;

    public AdministratorService(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    // prices
    public String fetchProductPrices(String jwt, boolean fetchPictures) {
        log.info("fetchProductPrices from URL: {}", administratorPricesUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        MessageDto response = administratorService.callFetchProductPrices(jwt, fetchPictures, requestId, version);
        log.info("received response: {}", response.getMessage());
        return response.getMessage();
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerFetchProductPrices")
    public MessageDto callFetchProductPrices(String jwt, boolean fetchPictures, String requestId, String version) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(administratorPricesUrl)
                .queryParam("fetchPictures", fetchPictures);
        String url = builder.toUriString();

        MDCUtil.putAll("Administration", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<MessageDto> response = new RestTemplate().exchange(url, HttpMethod.GET, requestEntity,
                MessageDto.class);
        return response.getBody();
    }

    public MessageDto circuitBreakerFetchProductPrices(String jwt, boolean fetchPictures, String requestId,
                                                       String version) {
        MDCUtil.putAll("Administration", version, requestId);
        log.error("There was an error when calling fetchProductPrices, so circuit breaker was activated");
        return new MessageDto("Error while calling prices, circuit breaker method called");
    }

    public String fetchProductPricesSpecificShop(String jwt, String id, boolean fetchPictures) {
        String administratorUrlSpecificShop = String.format("%s/shop", administratorPricesUrl);
        log.info("fetchProductPricesSpecificShop from URL: {}", administratorUrlSpecificShop);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        MessageDto response = administratorService.callFetchProductPricesSpecificShop(jwt, id, fetchPictures, requestId,
                version);
        log.info("received response: {}", response.getMessage());
        return response.getMessage();
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerFetchProductPricesSpecificShop")
    public MessageDto callFetchProductPricesSpecificShop(String jwt, String id, boolean fetchPictures, String requestId,
                                                         String version) {
        String url = String.format("%s/shop/%s", administratorPricesUrl, id);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("fetchPictures", fetchPictures);
        url = builder.toUriString();

        MDCUtil.putAll("Administration", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<MessageDto> response = new RestTemplate().exchange(url, HttpMethod.GET, requestEntity,
                MessageDto.class);
        return response.getBody();
    }

    public MessageDto circuitBreakerFetchProductPricesSpecificShop(String jwt, String id, boolean fetchPictures,
                                                                   String requestId, String version) {
        MDCUtil.putAll("Administration", version, requestId);
        log.error("There was an error when calling fetchProductPricesSpecificShop, so circuit breaker was activated");
        return new MessageDto("Error while calling prices, circuit breaker method called");
    }

    // product
    public ProductWithIdDto createProduct(String jwt, ProductDto product) {
        log.info("createProduct from URL: {}", administratorProductUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        ProductWithIdDto response = administratorService.callCreateProduct(jwt, product, requestId, version);
        log.info("received response: {}", response);
        return response;
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerCreateProduct")
    public ProductWithIdDto callCreateProduct(String jwt, ProductDto product, String requestId, String version) {
        MDCUtil.putAll("Administration", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(product, headers);
        ResponseEntity<ProductWithIdDto> response = new RestTemplate().exchange(administratorProductUrl,
                HttpMethod.POST, requestEntity, ProductWithIdDto.class);
        return response.getBody();
    }

    public ProductWithIdDto circuitBreakerCreateProduct(String jwt, ProductDto product, String requestId,
                                                        String version) {
        MDCUtil.putAll("Administration", version, requestId);
        log.error("There was an error when calling createProduct, so circuit breaker was activated");
        return null;
    }

    public String deleteProduct(String jwt, ProductIdDto productId) {
        log.info("deleteProduct from URL: {}", administratorProductUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        MessageDto response = administratorService.callDeleteProduct(jwt, productId, requestId, version);
        log.info("received response: {}", response.getMessage());
        return response.getMessage();
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerDeleteProduct")
    public MessageDto callDeleteProduct(String jwt, ProductIdDto productId, String requestId, String version) {
        MDCUtil.putAll("Administration", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(productId, headers);
        ResponseEntity<MessageDto> response = new RestTemplate().exchange(administratorProductUrl, HttpMethod.DELETE,
                requestEntity, MessageDto.class);
        return response.getBody();
    }

    public MessageDto circuitBreakerDeleteProduct(String jwt, ProductIdDto productId, String requestId,
                                                  String version) {
        MDCUtil.putAll("Administration", version, requestId);
        log.error("There was an error when calling deleteProduct, so circuit breaker was activated");
        return new MessageDto("Error while calling products, circuit breaker method called");
    }

    public String updateProduct(String jwt, ProductWithIdDto productWithId) {
        log.info("updateProduct from URL: {}", administratorProductUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        MessageDto response = administratorService.callUpdateProduct(jwt, productWithId, requestId, version);
        log.info("received response: {}", response.getMessage());
        return response.getMessage();
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerUpdateProduct")
    public MessageDto callUpdateProduct(String jwt, ProductWithIdDto productWithId, String requestId, String version) {
        MDCUtil.putAll("Administration", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(productWithId, headers);
        ResponseEntity<MessageDto> response = new RestTemplate().exchange(administratorProductUrl, HttpMethod.PUT,
                requestEntity, MessageDto.class);
        return response.getBody();
    }

    public MessageDto circuitBreakerUpdateProduct(String jwt, ProductWithIdDto productWithId, String requestId, String version) {
        MDCUtil.putAll("Administration", version, requestId);
        log.error("There was an error when calling updateProduct, so circuit breaker was activated");
        return new MessageDto("Error while calling products, circuit breaker method called");
    }

    // productShopHistory
    // productType
    // role
    // shop
    // user

}
