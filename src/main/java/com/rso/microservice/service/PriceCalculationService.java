package com.rso.microservice.service;

import com.google.gson.Gson;
import com.rso.microservice.api.dto.pricecalculation.PriceCalculationResponseDto;
import com.rso.microservice.api.dto.pricecalculation.ProductCalculationDto;
import com.rso.microservice.api.graphql.PriceCalculationGraphQLRequestBody;
import com.rso.microservice.api.graphql.PriceCalculationGraphQLResponseBody;
import com.rso.microservice.api.mapper.GraphQLMapper;
import com.rso.microservice.util.GraphQLSchemaReaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;

@Service
@RefreshScope
public class PriceCalculationService {
    private static final Logger log = LoggerFactory.getLogger(PriceCalculationService.class);

    @Value("${microservice.price-calculation-graphql.url}")
    private String priceCalculationGraphQLUrl;

    private final GraphQLMapper mapper;

    public PriceCalculationService(GraphQLMapper mapper) {
        this.mapper = mapper;
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

            return mapper.toModel(response);
        } catch (IOException e) {
            log.error("There was an error calling calculate price", e);
            return null;
        }
    }

    public PriceCalculationGraphQLResponseBody callCalculatePriceGraphQL(PriceCalculationGraphQLRequestBody requestBody) {
        WebClient webClient = WebClient.builder().build();
        return webClient.post()
                .uri(priceCalculationGraphQLUrl)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(PriceCalculationGraphQLResponseBody.class).block();
    }

    public PriceCalculationGraphQLResponseBody circuitBreaker(PriceCalculationGraphQLRequestBody requestBody) {
        return new PriceCalculationGraphQLResponseBody();
    }

}
