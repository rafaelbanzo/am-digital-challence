package com.gft.amdc.service;

import com.gft.amdc.domain.Coordinates;
import com.gft.amdc.domain.Shop;
import com.gft.amdc.external.client.GoogleMapsClient;
import com.gft.amdc.repository.ShopConcurrentHashMap;
import com.gft.amdc.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by RLBO on 24/04/2017.
 */
@Component
public class ShopService {

    @Autowired
    private ShopRepository shops;

    @Autowired
    GoogleMapsClient googleMapsClient;

    public Shop create(Shop shop) {

        //Delayed search of the longitude and latitude
        googleMapsClient.obtainCoordinates(shop);

        return shops.create(shop);

    }

    public Shop getNearestShop(Coordinates coordinates) {

        return shops.getNearestShop(coordinates);
    }

    public Shop retrieve(String name) {

        return shops.retrieve(name);

    }



}
