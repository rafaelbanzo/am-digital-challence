package com.gft.amdc.external.client;

import com.gft.amdc.controller.GlobalExceptionHandler;
import com.gft.amdc.domain.Coordinates;
import com.gft.amdc.domain.Shop;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.PendingResult;
import com.google.maps.model.GeocodingResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.logging.Level;

@Component
public class GoogleMapsClient implements MapsClient {

    @Value("${googleMaps.apyKey}")
    private String API_KEY;

    private static Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    public CompletableFuture<Coordinates> obtainCoordinates(Shop shop) {

        GeoApiContext context = new GeoApiContext().setApiKey(API_KEY);

        CompletableFuture<Coordinates> coordinatesCompletableFuture = new CompletableFuture<Coordinates>();

        GeocodingApiRequest req = GeocodingApi.geocode(context, shop.getShopAddress().getPostCode());

        // Asynchronous call.
        // The longitude and latitude of the postcode of the shop
        req.setCallback(new PendingResult.Callback<GeocodingResult[]>() {

            @Override
            public void onResult(GeocodingResult[] results) {
                //TODO: In a future version, the case where the data received does not contain the longitude and latitude should be treated
                if (results != null && results.length > 0 && results[0].geometry != null && results[0].geometry.location != null) {
                    coordinatesCompletableFuture.complete(new Coordinates(results[0].geometry.location.lat, results[0].geometry.location.lng));
                }
            }

            @Override
            public void onFailure(Throwable e) {
                //TODO: In a future version, the error should be treated
                logger.log(Level.WARNING, e.getMessage(), e);
            }
        });

        return coordinatesCompletableFuture;
    }
}
