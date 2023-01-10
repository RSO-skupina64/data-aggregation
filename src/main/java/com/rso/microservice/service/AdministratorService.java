package com.rso.microservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rso.microservice.api.dto.MessageDto;
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

    public AdministratorService(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

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

    public MessageDto circuitBreakerFetchProductPrices(String jwt, boolean fetchPictures, String requestId, String version) {
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
                                                                   String requestId, String version, Throwable ex) {
        MDCUtil.putAll("Administration", version, requestId);
        log.error(String.valueOf(ex));
        log.error("There was an error when calling fetchProductPricesSpecificShop, so circuit breaker was activated");
        return new MessageDto("Error while calling prices, circuit breaker method called");
    }

}
