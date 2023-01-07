package com.rso.microservice.service;

import com.rso.microservice.api.dto.administration.ShopsArrayResponseDto;
import com.rso.microservice.api.mapper.ShopMapper;
import com.rso.microservice.grpc.ShopServiceGrpc;
import com.rso.microservice.grpc.Shops.ShopsRequest;
import com.rso.microservice.grpc.Shops.ShopsResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class ShopService {

    @GrpcClient("shops")
    private ShopServiceGrpc.ShopServiceBlockingStub shopServiceStub;

    private final ShopMapper shopMapper;

    public ShopService(ShopMapper shopMapper) {
        this.shopMapper = shopMapper;
    }

    public ShopsArrayResponseDto getShops() {
        ShopsResponse response = shopServiceStub.getShops(ShopsRequest.newBuilder().build());

        ShopsArrayResponseDto res = new ShopsArrayResponseDto();
        res.setCount((int) response.getCount());
        res.setShops(shopMapper.toModel(response.getShopsList()));

        return res;
    }


}
