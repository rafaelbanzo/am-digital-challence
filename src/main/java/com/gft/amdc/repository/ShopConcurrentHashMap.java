package com.gft.amdc.repository;

import com.gft.amdc.domain.Shop;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Created by RLBO on 20/04/2017.
 */
@Component
public class ShopConcurrentHashMap implements ShopRepository {

    private static Logger logger = Logger.getLogger(ShopConcurrentHashMap.class.getName());

    public ConcurrentHashMap<String, Shop> shopsMap;

    @PostConstruct
    public void initIt() throws Exception {
        shopsMap = new ConcurrentHashMap<>();
    }

    public Shop create(Shop shop) {
        return shopsMap.put(shop.getShopName(), shop);
    }

    public Shop retrieve(String name) {
        return shopsMap.get(name);
    }

    public Collection<Shop> findAll() {
        return shopsMap.values();
    }

    public Shop update (Shop shop){
        Shop savedShop = retrieve(shop.getShopName());
        if (shop.getTimeStamp().equals(savedShop.getTimeStamp())) {
            return shopsMap.replace(shop.getShopName(), shop);
        } else {
            logger.log(Level.WARNING, "Trying to update wrong shop version");
            return null;
        }
    }

}
