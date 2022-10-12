package com.udacity.vehicles.client.prices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Implements a class to interface with the Pricing Client for price data.
 */
@Component
public class PriceClient {

    private static final Logger log = LoggerFactory.getLogger(PriceClient.class);

    private final WebClient client;

    public PriceClient(@Qualifier("pricing") WebClient pricing) {
        this.client = pricing;
    }

    // In a real-world application we'll want to add some resilience
    // to this method with retries/CB/failover capabilities
    // We may also want to cache the results so we don't need to
    // do a request every time

    /**
     * Gets a vehicle price from the pricing client, given vehicle ID.
     *
     * @param vehicleId ID number of the vehicle for which to get the price
     * @return Currency and price of the requested vehicle,
     * error message that the vehicle ID is invalid, or note that the
     * service is down.
     */
    public String getPrice(Long vehicleId) {
        try {
            Price price = client
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/prices/search/findByVehicleId")
                            .queryParam("vehicleId", vehicleId)
                            .build()
                    )
                    .retrieve().bodyToMono(Price.class).block();

            return String.format("%s %s", price.getCurrency(), price.getPrice());

        } catch (Exception e) {
            log.error("Unexpected error retrieving price for vehicle {}", vehicleId, e);
        }
        return "(consult price)";
    }

//    public void updatePrice(Long vehicleId) {
//        try {
//            Price price = client.put()
//                    .uri(uriBuilder ->
//                            uriBuilder
//                                    .path("/prices")
////                                    .queryParam("vehicleId", vehicleId)
//                                    .build()
//                    ).retrieve().bodyToMono(Price.class).block();
//        } catch (Exception e) {
//            log.error("Unexpected error retrieving price for vehicle {}", vehicleId, e);
//        }
////        return "(consult price)";
//    }
//    public void addPrice(Long vehicleId, String carPrice)
//    {
//        try {
//            Map<String,String> requestBody = new HashMap<>();
//            requestBody.put("vehicleId",vehicleId.toString());
//            requestBody.put("price",carPrice);
//            Price price = client
//                    .post()
//                    .uri(uriBuilder -> uriBuilder
//                            .path("/prices")
//                            .build()
//                    )
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .body(Mono.just(requestBody),Map.class)
//                    .retrieve().bodyToMono(Price.class).block();
//        } catch (Exception e) {
//            log.error("Unexpected error retrieving price for vehicle {}", vehicleId, e);
//        }
//
//    }

}
