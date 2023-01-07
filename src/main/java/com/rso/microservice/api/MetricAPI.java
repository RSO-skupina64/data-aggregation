package com.rso.microservice.api;

import com.rso.microservice.api.dto.ErrorDto;
import com.rso.microservice.api.dto.MetricsDto;
import com.rso.microservice.service.MetricsService;
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
@RequestMapping("/metrics")
@Tag(name = "Metric")
public class MetricAPI {

    private final MetricsService metricsService;

    public MetricAPI(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping(value = "administration", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all metrics", description = "Get all custom implemented metrics")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "metrics", content = @Content(schema = @Schema(implementation = MetricsDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<MetricsDto> getMetrics() {
        return ResponseEntity.status(HttpStatus.OK).body(metricsService.returnMetrics());
    }

}
