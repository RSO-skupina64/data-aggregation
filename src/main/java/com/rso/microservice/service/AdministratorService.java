package com.rso.microservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rso.microservice.api.dto.MessageDto;
import com.rso.microservice.api.dto.administration.*;
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
    private String administratorProductTypeUrl;
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

    public MessageDto circuitBreakerUpdateProduct(String jwt, ProductWithIdDto productWithId, String requestId,
                                                  String version) {
        MDCUtil.putAll("Administration", version, requestId);
        log.error("There was an error when calling updateProduct, so circuit breaker was activated");
        return new MessageDto("Error while calling products, circuit breaker method called");
    }

    // productShopHistory
    public ProductShopHistoryWithIdDto createProductShopHistory(String jwt, ProductShopHistoryDto productShopHistory) {
        log.info("createProductShopHistory from URL: {}", administratorProductShopHistoryUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        ProductShopHistoryWithIdDto response = administratorService.callCreateProductShopHistory(jwt,
                productShopHistory, requestId, version);
        log.info("received response: {}", response);
        return response;
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerCreateProductShopHistory")
    public ProductShopHistoryWithIdDto callCreateProductShopHistory(String jwt,
                                                                    ProductShopHistoryDto productShopHistory,
                                                                    String requestId, String version) {
        MDCUtil.putAll("Administration", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(productShopHistory, headers);
        ResponseEntity<ProductShopHistoryWithIdDto> response = new RestTemplate().exchange(
                administratorProductShopHistoryUrl,
                HttpMethod.POST, requestEntity, ProductShopHistoryWithIdDto.class);
        return response.getBody();
    }

    public ProductShopHistoryWithIdDto circuitBreakerCreateProductShopHistory(String jwt,
                                                                              ProductShopHistoryDto productShopHistory,
                                                                              String requestId, String version) {
        MDCUtil.putAll("Administration", version, requestId);
        log.error("There was an error when calling createProductShopHistory, so circuit breaker was activated");
        return null;
    }

    public String deleteProductShopHistory(String jwt, ProductIdDto productId) {
        log.info("deleteProductShopHistory from URL: {}", administratorProductShopHistoryUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        MessageDto response = administratorService.callDeleteProductShopHistory(jwt, productId, requestId, version);
        log.info("received response: {}", response.getMessage());
        return response.getMessage();
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerDeleteProductShopHistory")
    public MessageDto callDeleteProductShopHistory(String jwt, ProductIdDto productId, String requestId,
                                                   String version) {
        MDCUtil.putAll("Administration", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(productId, headers);
        ResponseEntity<MessageDto> response = new RestTemplate().exchange(administratorProductShopHistoryUrl,
                HttpMethod.DELETE, requestEntity, MessageDto.class);
        return response.getBody();
    }

    public MessageDto circuitBreakerDeleteProductShopHistory(String jwt, ProductIdDto productId, String requestId,
                                                             String version) {
        MDCUtil.putAll("Administration", version, requestId);
        log.error("There was an error when calling deleteProductShopHistory, so circuit breaker was activated");
        return new MessageDto("Error while calling product shop history circuit breaker method called");
    }

    public String updateProductShopHistory(String jwt, ProductShopHistoryWithIdDto productShopHistoryWithId) {
        log.info("updateProductShopHistory from URL: {}", administratorProductShopHistoryUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        MessageDto response = administratorService.callUpdateProductShopHistory(jwt, productShopHistoryWithId,
                requestId, version);
        log.info("received response: {}", response.getMessage());
        return response.getMessage();
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerUpdateProductShopHistory")
    public MessageDto callUpdateProductShopHistory(String jwt, ProductShopHistoryWithIdDto productShopHistoryWithId,
                                                   String requestId, String version) {
        MDCUtil.putAll("Administration", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(productShopHistoryWithId, headers);
        ResponseEntity<MessageDto> response = new RestTemplate().exchange(administratorProductShopHistoryUrl,
                HttpMethod.PUT, requestEntity, MessageDto.class);
        return response.getBody();
    }

    public MessageDto circuitBreakerUpdateProductShopHistory(String jwt,
                                                             ProductShopHistoryWithIdDto productShopHistoryWithId,
                                                             String requestId, String version) {
        MDCUtil.putAll("Administration", version, requestId);
        log.error("There was an error when calling updateProductShopHistory, so circuit breaker was activated");
        return new MessageDto("Error while calling product shop history circuit breaker method called");
    }

    // productType
    public ProductTypeWithIdDto createProductType(String jwt, ProductTypeDto productType) {
        log.info("createProductType from URL: {}", administratorProductTypeUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        ProductTypeWithIdDto response = administratorService.callCreateProductType(jwt, productType, requestId,
                version);
        log.info("received response: {}", response);
        return response;
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerCreateProductType")
    public ProductTypeWithIdDto callCreateProductType(String jwt, ProductTypeDto productType, String requestId,
                                                      String version) {
        MDCUtil.putAll("Administration", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(productType, headers);
        ResponseEntity<ProductTypeWithIdDto> response = new RestTemplate().exchange(administratorProductTypeUrl,
                HttpMethod.POST, requestEntity, ProductTypeWithIdDto.class);
        return response.getBody();
    }

    public ProductTypeWithIdDto circuitBreakerCreateProductType(String jwt, ProductTypeDto productType,
                                                                String requestId, String version) {
        MDCUtil.putAll("Administration", version, requestId);
        log.error("There was an error when calling createProductType, so circuit breaker was activated");
        return null;
    }

    public String deleteProductType(String jwt, ProductTypeIdDto productTypeId) {
        log.info("deleteProductType from URL: {}", administratorProductTypeUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        MessageDto response = administratorService.callDeleteProductType(jwt, productTypeId, requestId, version);
        log.info("received response: {}", response.getMessage());
        return response.getMessage();
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerDeleteProductType")
    public MessageDto callDeleteProductType(String jwt, ProductTypeIdDto productTypeId, String requestId,
                                            String version) {
        MDCUtil.putAll("Administration", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(productTypeId, headers);
        ResponseEntity<MessageDto> response = new RestTemplate().exchange(administratorProductTypeUrl,
                HttpMethod.DELETE, requestEntity, MessageDto.class);
        return response.getBody();
    }

    public MessageDto circuitBreakerDeleteProductType(String jwt, ProductTypeIdDto productTypeId, String requestId,
                                                      String version) {
        MDCUtil.putAll("Administration", version, requestId);
        log.error("There was an error when calling deleteProductType, so circuit breaker was activated");
        return new MessageDto("Error while calling product shop history circuit breaker method called");
    }

    public String updateProductType(String jwt, ProductTypeWithIdDto productTypeWithId) {
        log.info("updateProductType from URL: {}", administratorProductTypeUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        MessageDto response = administratorService.callUpdateProductType(jwt, productTypeWithId, requestId, version);
        log.info("received response: {}", response.getMessage());
        return response.getMessage();
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerUpdateProductType")
    public MessageDto callUpdateProductType(String jwt, ProductTypeWithIdDto productTypeWithId, String requestId,
                                            String version) {
        MDCUtil.putAll("Administration", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(productTypeWithId, headers);
        ResponseEntity<MessageDto> response = new RestTemplate().exchange(administratorProductTypeUrl, HttpMethod.PUT,
                requestEntity, MessageDto.class);
        return response.getBody();
    }

    public MessageDto circuitBreakerUpdateProductType(String jwt, ProductTypeWithIdDto productTypeWithId,
                                                      String requestId, String version) {
        MDCUtil.putAll("Administration", version, requestId);
        log.error("There was an error when calling updateProductType, so circuit breaker was activated");
        return new MessageDto("Error while calling product shop history circuit breaker method called");
    }

    // role
    public RoleWithIdDto createRole(String jwt, RoleDto role) {
        log.info("createRole from URL: {}", administratorRoleUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        RoleWithIdDto response = administratorService.callCreateRole(jwt, role, requestId, version);
        log.info("received response: {}", response);
        return response;
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerCreateRole")
    public RoleWithIdDto callCreateRole(String jwt, RoleDto role, String requestId, String version) {
        MDCUtil.putAll("Administration", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(role, headers);
        ResponseEntity<RoleWithIdDto> response = new RestTemplate().exchange(administratorRoleUrl, HttpMethod.POST,
                requestEntity, RoleWithIdDto.class);
        return response.getBody();
    }

    public RoleWithIdDto circuitBreakerCreateRole(String jwt, RoleDto role, String requestId, String version) {
        MDCUtil.putAll("Administration", version, requestId);
        log.error("There was an error when calling createRole, so circuit breaker was activated");
        return null;
    }

    public String deleteRole(String jwt, RoleIdDto roleId) {
        log.info("deleteRole from URL: {}", administratorRoleUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        MessageDto response = administratorService.callDeleteRole(jwt, roleId, requestId, version);
        log.info("received response: {}", response.getMessage());
        return response.getMessage();
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerDeleteRole")
    public MessageDto callDeleteRole(String jwt, RoleIdDto roleId, String requestId, String version) {
        MDCUtil.putAll("Administration", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(roleId, headers);
        ResponseEntity<MessageDto> response = new RestTemplate().exchange(administratorRoleUrl, HttpMethod.DELETE,
                requestEntity, MessageDto.class);
        return response.getBody();
    }

    public MessageDto circuitBreakerDeleteRole(String jwt, RoleIdDto roleId, String requestId, String version) {
        MDCUtil.putAll("Administration", version, requestId);
        log.error("There was an error when calling deleteRole, so circuit breaker was activated");
        return new MessageDto("Error while calling product shop history circuit breaker method called");
    }

    public String updateRole(String jwt, RoleWithIdDto roleWithId) {
        log.info("updateRole from URL: {}", administratorRoleUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        MessageDto response = administratorService.callUpdateRole(jwt, roleWithId, requestId, version);
        log.info("received response: {}", response.getMessage());
        return response.getMessage();
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerUpdateRole")
    public MessageDto callUpdateRole(String jwt, RoleWithIdDto roleWithId, String requestId, String version) {
        MDCUtil.putAll("Administration", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(roleWithId, headers);
        ResponseEntity<MessageDto> response = new RestTemplate().exchange(administratorRoleUrl, HttpMethod.PUT,
                requestEntity, MessageDto.class);
        return response.getBody();
    }

    public MessageDto circuitBreakerUpdateRole(String jwt, RoleWithIdDto roleWithId, String requestId, String version) {
        MDCUtil.putAll("Administration", version, requestId);
        log.error("There was an error when calling updateRole, so circuit breaker was activated");
        return new MessageDto("Error while calling product shop history circuit breaker method called");
    }

    // shop

    // user
    public String deleteUser(String jwt, UserIdDto userId) {
        log.info("deleteUser from URL: {}", administratorUserUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        MessageDto response = administratorService.callDeleteUser(jwt, userId, requestId, version);
        log.info("received response: {}", response.getMessage());
        return response.getMessage();
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerDeleteUser")
    public MessageDto callDeleteUser(String jwt, UserIdDto userId, String requestId,
                                     String version) {
        MDCUtil.putAll("Administration", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(userId, headers);
        ResponseEntity<MessageDto> response = new RestTemplate().exchange(administratorUserUrl, HttpMethod.DELETE,
                requestEntity, MessageDto.class);
        return response.getBody();
    }

    public MessageDto circuitBreakerDeleteUser(String jwt, UserIdDto userId, String requestId, String version) {
        MDCUtil.putAll("Administration", version, requestId);
        log.error("There was an error when calling deleteUser, so circuit breaker was activated");
        return new MessageDto("Error while calling product shop history circuit breaker method called");
    }

    public String updateUser(String jwt, UserDto user) {
        log.info("updateUser from URL: {}", administratorUserUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        MessageDto response = administratorService.callUpdateUser(jwt, user, requestId, version);
        log.info("received response: {}", response.getMessage());
        return response.getMessage();
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerUpdateUser")
    public MessageDto callUpdateUser(String jwt, UserDto user, String requestId, String version) {
        MDCUtil.putAll("Administration", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(user, headers);
        ResponseEntity<MessageDto> response = new RestTemplate().exchange(administratorUserUrl, HttpMethod.PUT,
                requestEntity, MessageDto.class);
        return response.getBody();
    }

    public MessageDto circuitBreakerUpdateUser(String jwt, UserDto user, String requestId, String version) {
        MDCUtil.putAll("Administration", version, requestId);
        log.error("There was an error when calling updateUser, so circuit breaker was activated");
        return new MessageDto("Error while calling product shop history circuit breaker method called");
    }

}
