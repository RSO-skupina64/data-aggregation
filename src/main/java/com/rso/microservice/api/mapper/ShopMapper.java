package com.rso.microservice.api.mapper;

import com.rso.microservice.api.dto.administration.ShopDto;
import com.rso.microservice.grpc.Shops.ShopGrpc;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShopMapper {

    List<ShopDto> toModel(List<ShopGrpc> shops);

    ShopDto toModel(ShopGrpc shop);

}
