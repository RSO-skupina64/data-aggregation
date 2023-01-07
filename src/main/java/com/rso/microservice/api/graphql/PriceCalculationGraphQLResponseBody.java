package com.rso.microservice.api.graphql;

import java.util.List;

public class PriceCalculationGraphQLResponseBody {

    private CalculatePriceResponse data;

    public CalculatePriceResponse getData() {
        return data;
    }

    public static class CalculatePriceResponse {

        private ShopPricesResponse calculatePrice;

        public ShopPricesResponse getCalculatePrice() {
            return calculatePrice;
        }
    }

    public static class ShopPricesResponse {
        private List<ShopPriceGraphQL> shopPrices;

        public List<ShopPriceGraphQL> getShopPrices() {
            return shopPrices;
        }

    }

}
