package com.rso.microservice.service;

import com.rso.microservice.api.dto.administration.ShopsArrayResponseDto;
import com.rso.microservice.api.mapper.ShopMapper;
import com.rso.microservice.grpc.ShopServiceGrpc;
import com.rso.microservice.grpc.Shops.ShopsRequest;
import com.rso.microservice.grpc.Shops.ShopsResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class ShopService {
	private static final Logger log = LoggerFactory.getLogger(ShopService.class);

	@Value("${config.grpc.client.shop.address}")
	private String grpcClientAddress;

	@Value("${config.grpc.client.shop.port}")
	private int grpcClientPort;

	@GrpcClient("shops")
	private ShopServiceGrpc.ShopServiceBlockingStub shopServiceStub;

	private final ShopMapper shopMapper;

	public ShopService(ShopMapper shopMapper) {
		this.shopMapper = shopMapper;
	}

	public ShopsArrayResponseDto getShops() {
		log.info("config server grpc address: {}", grpcClientAddress);
		if (grpcClientAddress != null && !grpcClientAddress.contains("localhost")) {
			log.info("changing gprc address to {} and port to {}", grpcClientAddress, grpcClientPort);
			ManagedChannel channel = ManagedChannelBuilder.forAddress(grpcClientAddress, grpcClientPort)
					.usePlaintext()
					.build();
			shopServiceStub = ShopServiceGrpc.newBlockingStub(channel);
		}
		log.info("calling via Grpc protocol");
		ShopsResponse response = shopServiceStub.getShops(ShopsRequest.newBuilder().build());
		log.info("received response of {} shop(s)", response.getCount());
		ShopsArrayResponseDto res = new ShopsArrayResponseDto();
		res.setCount((int) response.getCount());
		res.setShops(shopMapper.toModel(response.getShopsList()));

		return res;
	}


}
