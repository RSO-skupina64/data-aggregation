package com.rso.microservice.api.mapper;

import com.rso.microservice.api.dto.pricecalculation.PriceCalculationResponseDto;
import com.rso.microservice.api.dto.pricecalculation.ShopPriceDto;
import com.rso.microservice.api.graphql.PriceCalculationGraphQLResponseBody;
import com.rso.microservice.api.graphql.ShopPriceGraphQL;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GraphQLMapper {

    @Mapping(source = "data.calculatePrice.shopPrices", target = "shopPrices")
    PriceCalculationResponseDto toModel(PriceCalculationGraphQLResponseBody response);

    List<ShopPriceDto> toModel(List<ShopPriceGraphQL> prices);

    ShopPriceDto toModel(ShopPriceGraphQL price);

}
