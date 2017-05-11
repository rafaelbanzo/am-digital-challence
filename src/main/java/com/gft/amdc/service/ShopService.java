package com.gft.amdc.service;

import com.gft.amdc.domain.Coordinates;
import com.gft.amdc.domain.Shop;
import com.gft.amdc.external.client.MapsClient;
import com.gft.amdc.repository.ShopRepository;
import com.gft.amdc.util.ShopDistanceComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by RLBO on 24/04/2017.
 */
@Component
public class ShopService {

    @Autowired
    private ShopRepository shops;

    @Autowired
    MapsClient mapsClient;

    public Shop create(Shop shop) {

        obtainCoordinates (shop);

        return shops.create(shop);

    }


    public Shop getNearestShop(Coordinates coordinates) {

        Collection<Shop> shopCollection = shops.findAll();

        Optional<Shop> nearestShop = shopCollection.stream().sorted(new ShopDistanceComparator(coordinates)).findFirst();
        if (nearestShop.isPresent())
            return nearestShop.get();
        else
            return null;

    }

    public Shop retrieve(String name) {

        return shops.retrieve(name);

    }

    private void obtainCoordinates(Shop shop) {
        //Delayed search of the longitude and latitude
        mapsClient.obtainCoordinates(shop).thenAccept(coordinates -> {
            shop.getShopAddress().setCoordinates(coordinates);
            shops.update(shop);
        });
    }
}
