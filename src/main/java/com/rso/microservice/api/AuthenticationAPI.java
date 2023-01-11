package com.rso.microservice.api;

import com.rso.microservice.api.dto.ErrorDto;
import com.rso.microservice.api.dto.MessageDto;
import com.rso.microservice.api.dto.authentication.*;
import com.rso.microservice.service.AuthenticationService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
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
@RequestMapping("/authentication")
@OpenAPIDefinition(info = @Info(title = "Data aggregation API",
        description = "This is API documentation for Data Aggregation Microservice",
        version = "0.1"))
@Tag(name = "Authentication")
public class AuthenticationAPI {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationAPI.class);

    private final AuthenticationService authenticationService;

    public AuthenticationAPI(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Register a new user",
            description = "Given an username and password, register a new user into the application")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User successfully registered",
                    content = @Content(schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "400", description = "User already registered",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<MessageDto> register(@Valid @RequestBody RegistrationRequestDto registrationRequest) {
        log.info("register: ENTRY");
        String response = authenticationService.register(registrationRequest);
        log.info("register: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(response));
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Login a user",
            description = "Given an username and password, log in an user into the application")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Log in successful",
                    content = @Content(schema = @Schema(implementation = LoginResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request.",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Invalid credentials",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        log.info("login: ENTRY");
        LoginResponseDto loginResponse = authenticationService.login(loginRequest);
        log.info("login: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

    @PostMapping(value = "/check-user-role", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Check user's role fits",
            description = "Check if user has role that is specified in the request")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User role matches requested",
                    content = @Content(schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "400", description = "User role doesn't match requested",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<MessageDto> checkUserRole(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                          @Valid @RequestBody CheckUserRoleRequestDto checkUserRoleRequest) {
        log.info("checkUserRole: ENTRY");
        String response = authenticationService.checkUserRole(jwt, checkUserRoleRequest);
        log.info("checkUserRole: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(response));
    }

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get logged in User extended info",
            description = "Get profile information about currently logged in user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User information successfully fetched",
                    content = @Content(schema = @Schema(implementation = UserDetailsDto.class))),
            @ApiResponse(responseCode = "400", description = "User not authenticated",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<UserDetailsDto> getUserProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
        log.info("getUserProfile: ENTRY");
        UserDetailsDto userDetails = authenticationService.getUserProfile(jwt);
        log.info("getUserProfile: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(userDetails);
    }

    @PutMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update User profile",
            description = "Update profile information about currently logged in user")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User profile update successful"),
            @ApiResponse(responseCode = "400", description = "User not authenticated",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<?> updateUserProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                               @Valid @RequestBody UserDetailsDto userDetails) {
        log.info("updateUserProfile: ENTRY");
        userDetails = authenticationService.updateUserProfile(jwt, userDetails);
        log.info("updateUserProfile: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(userDetails));
    }

    @PutMapping(value = "/user/change-password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Change user password", description = "Changes user password")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User profile update successful"),
            @ApiResponse(responseCode = "400", description = "User not authenticated",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<?> changePassword(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                            @Valid @RequestBody ChangePasswordRequestDto changePasswordRequest) {
        log.info("changePassword: ENTRY");
        String response = authenticationService.changePassword(jwt, changePasswordRequest);
        log.info("changePassword: EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(response));
    }

}
