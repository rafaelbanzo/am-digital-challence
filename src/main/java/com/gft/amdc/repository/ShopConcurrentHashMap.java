package com.gft.amdc.repository;

import com.gft.amdc.domain.Coordinates;
import com.gft.amdc.domain.Shop;
import com.gft.amdc.service.ShopService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by RLBO on 20/04/2017.
 */
@Component
public class ShopConcurrentHashMap implements ShopRepository {

    public ConcurrentHashMap<String, Shop> shopsMap;

    @PostConstruct
    public void initIt() throws Exception {
        shopsMap = new ConcurrentHashMap<>();
    }

    public Shop create (Shop shop) {
        return shopsMap.put(shop.getShopName(), shop);
    }

    public Shop retrieve (String name){
        return shopsMap.get(name);
    }

    public Shop getNearestShop(Coordinates coordinates) {

        if (shopsMap.isEmpty())
            return null;

        List<Shop> shopList = new ArrayList<Shop>(shopsMap.values());

        Collections.sort(shopList, new ShopConcurrentHashMap.ShopDistanceComparator(coordinates));

        return shopList.get(0);
    }


    static class ShopDistanceComparator implements Comparator<Shop> {

        public Coordinates coordinates;

        public ShopDistanceComparator(Coordinates coordinates) {
            this.coordinates = coordinates;
        }

        public int compare(Shop s1, Shop s2) {
            return s1.distance(coordinates).compareTo(s2.distance(coordinates));
        }
    }
}
