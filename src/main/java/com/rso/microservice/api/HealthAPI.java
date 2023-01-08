package com.rso.microservice.api;

import com.rso.microservice.api.dto.ErrorDto;
import com.rso.microservice.api.dto.MessageDto;
import com.rso.microservice.api.dto.MetricsDto;
import com.rso.microservice.repository.PingRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@Tag(name = "Health")
public class HealthAPI {

    private final PingRepository pingRepository;

    public HealthAPI(PingRepository pingRepository) {
        this.pingRepository = pingRepository;
    }

    @GetMapping(value = "/database", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Check Database connection", description = "Check Database connection")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "metrics", content = @Content(schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<MessageDto> pingDatabase() {
        Integer value = pingRepository.ping();
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(String.format("Database responded with %d so it is UP", value)));
    }

}
