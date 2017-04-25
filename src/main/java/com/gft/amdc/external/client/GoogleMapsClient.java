package com.gft.amdc.external.client;

import com.gft.amdc.domain.Coordinates;
import com.gft.amdc.domain.Shop;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.PendingResult;
import com.google.maps.model.GeocodingResult;
import org.springframework.stereotype.Component;

@Component
public class GoogleMapsClient {

    private static final String API_KEY = "AIzaSyAZ-BgMm5DPgalGugyVcsg0F30oFwC-K7w";

    public void obtainCoordinates(Shop shop) {

        GeoApiContext context = new GeoApiContext().setApiKey(API_KEY);
        GeocodingApiRequest req = GeocodingApi.geocode(context, shop.getShopAddress().getPostCode());

        // Asynchronous call.
        // The longitude and latitude of the postcode of the shop
        req.setCallback(new PendingResult.Callback<GeocodingResult[]>() {

            @Override
            public void onResult(GeocodingResult[] results) {
                //TODO: In a future version, the case where the data received does not contain the longitude and latitude should be treated
                if (results != null && results.length > 0 && results[0].geometry != null && results[0].geometry.location != null) {
                    shop.getShopAddress().setCoordinates(new Coordinates(results[0].geometry.location.lng, results[0].geometry.location.lat));
                }
            }

            @Override
            public void onFailure(Throwable e) {
                //TODO: In a future version, the error should be treated
                e.printStackTrace();
            }
        });

    }
}
