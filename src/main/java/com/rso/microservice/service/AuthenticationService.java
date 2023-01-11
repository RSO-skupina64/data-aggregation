package com.rso.microservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rso.microservice.api.dto.MessageDto;
import com.rso.microservice.api.dto.authentication.*;
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

@Service
@RefreshScope
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    private final AuthenticationService authenticationService;

    @Value("${microservice.authentication.url}/authentication")
    private String authenticationAuthenticationUrl;
    @Value("${microservice.authentication.url}/profile")
    private String authenticationUserProfileUrl;

    public AuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public String register(RegistrationRequestDto registrationRequest) {
        log.info("register from URL: {}", authenticationAuthenticationUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        MessageDto response = authenticationService.callRegister(registrationRequest, requestId, version);
        log.info("received response: {}", response.getMessage());
        return response.getMessage();
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerRegister")
    public MessageDto callRegister(RegistrationRequestDto registrationRequest, String requestId, String version) {
        String url = String.format("%s/register", authenticationAuthenticationUrl);

        MDCUtil.putAll("Data aggregation", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(registrationRequest, headers);
        ResponseEntity<MessageDto> response = new RestTemplate().exchange(url, HttpMethod.POST, requestEntity,
                MessageDto.class);
        return response.getBody();
    }

    public MessageDto circuitBreakerRegister(RegistrationRequestDto registrationRequest, String requestId,
                                             String version) {
        MDCUtil.putAll("Data aggregation", version, requestId);
        log.error("There was an error when calling register, so circuit breaker was activated");
        return new MessageDto("Error while calling authentication, circuit breaker method called");
    }

    public LoginResponseDto login(LoginRequestDto loginRequest) {
        log.info("login from URL: {}", authenticationAuthenticationUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        LoginResponseDto response = authenticationService.callLogin(loginRequest, requestId, version);
        log.info("received response: {}", response);
        return response;
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerLogin")
    public LoginResponseDto callLogin(LoginRequestDto loginRequest, String requestId, String version) {
        String url = String.format("%s/login", authenticationAuthenticationUrl);

        MDCUtil.putAll("Data aggregation", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(loginRequest, headers);
        ResponseEntity<LoginResponseDto> response = new RestTemplate().exchange(url, HttpMethod.POST, requestEntity,
                LoginResponseDto.class);
        return response.getBody();
    }

    public LoginResponseDto circuitBreakerLogin(LoginRequestDto loginRequest, String requestId, String version) {
        MDCUtil.putAll("Data aggregation", version, requestId);
        log.error("There was an error when calling login, so circuit breaker was activated");
        return null;
    }


    public String checkUserRole(String jwt, CheckUserRoleRequestDto checkUserRoleRequest) {
        log.info("checkUserRole from URL: {}", authenticationAuthenticationUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        MessageDto response = authenticationService.callCheckUserRole(jwt, checkUserRoleRequest, requestId, version);
        log.info("received response: {}", response.getMessage());
        return response.getMessage();
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerCheckUserRole")
    public MessageDto callCheckUserRole(String jwt, CheckUserRoleRequestDto checkUserRoleRequest, String requestId,
                                        String version) {
        String url = String.format("%s/check-user-role", authenticationAuthenticationUrl);

        MDCUtil.putAll("Data aggregation", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(checkUserRoleRequest, headers);
        ResponseEntity<MessageDto> response = new RestTemplate().exchange(url, HttpMethod.POST, requestEntity,
                MessageDto.class);
        return response.getBody();
    }

    public MessageDto circuitBreakerCheckUserRole(String jwt, CheckUserRoleRequestDto checkUserRoleRequest,
                                                  String requestId, String version) {
        MDCUtil.putAll("Data aggregation", version, requestId);
        log.error("There was an error when calling checkUserRole, so circuit breaker was activated");
        return new MessageDto("Error while calling authentication, circuit breaker method called");
    }

    public UserDetailsDto getUserProfile(String jwt) {
        log.info("getUserProfile from URL: {}", authenticationUserProfileUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        UserDetailsDto response = authenticationService.callGetUserProfile(jwt, requestId, version);
        log.info("received response: {}", response);
        return response;
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerGetUserProfile")
    public UserDetailsDto callGetUserProfile(String jwt, String requestId, String version) {
        String url = String.format("%s/user", authenticationUserProfileUrl);

        MDCUtil.putAll("Data aggregation", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<UserDetailsDto> response = new RestTemplate().exchange(url, HttpMethod.GET, requestEntity,
                UserDetailsDto.class);
        return response.getBody();
    }

    public UserDetailsDto circuitBreakerGetUserProfile(String jwt, String requestId, String version) {
        MDCUtil.putAll("Data aggregation", version, requestId);
        log.error("There was an error when calling getUserProfile, so circuit breaker was activated");
        return null;
    }

    public UserDetailsDto updateUserProfile(String jwt, UserDetailsDto userDetails) {
        log.info("updateUserProfile from URL: {}", authenticationUserProfileUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        UserDetailsDto response = authenticationService.callUpdateUserProfile(jwt, userDetails, requestId, version);
        log.info("received response: {}", response);
        return response;
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerUpdateUserProfile")
    public UserDetailsDto callUpdateUserProfile(String jwt, UserDetailsDto userDetails, String requestId, String version) {
        String url = String.format("%s/user", authenticationUserProfileUrl);

        MDCUtil.putAll("Data aggregation", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(userDetails, headers);
        ResponseEntity<UserDetailsDto> response = new RestTemplate().exchange(url, HttpMethod.PUT, requestEntity,
                UserDetailsDto.class);
        return response.getBody();
    }

    public UserDetailsDto circuitBreakerUpdateUserProfile(String jwt, UserDetailsDto userDetails, String requestId,
                                                      String version) {
        MDCUtil.putAll("Data aggregation", version, requestId);
        log.error("There was an error when calling updateUserProfile, so circuit breaker was activated");
        return null;
    }

    public String changePassword(String jwt, ChangePasswordRequestDto changePasswordRequest) {
        log.info("changePassword from URL: {}", authenticationUserProfileUrl);
        String requestId = MDCUtil.get(MDCUtil.MDCUtilKey.REQUEST_ID);
        String version = MDCUtil.get(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION);
        MessageDto response = authenticationService.callChangePassword(jwt, changePasswordRequest, requestId, version);
        log.info("received response: {}", response.getMessage());
        return response.getMessage();
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerChangePassword")
    public MessageDto callChangePassword(String jwt, ChangePasswordRequestDto changePasswordRequest, String requestId,
                                         String version) {
        String url = String.format("%s/user/change-password", authenticationUserProfileUrl);

        MDCUtil.putAll("Data aggregation", version, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
        headers.add("X-Request-Id", requestId);
        HttpEntity<?> requestEntity = new HttpEntity<>(changePasswordRequest, headers);
        ResponseEntity<MessageDto> response = new RestTemplate().exchange(url, HttpMethod.PUT,
                requestEntity, MessageDto.class);
        return response.getBody();
    }

    public MessageDto circuitBreakerChangePassword(String jwt, ChangePasswordRequestDto changePasswordRequest,
                                                   String requestId,
                                                   String version) {
        MDCUtil.putAll("Data aggregation", version, requestId);
        log.error("There was an error when calling changePassword, so circuit breaker was activated");
        return new MessageDto("Error while calling authentication, circuit breaker method called");
    }

}
