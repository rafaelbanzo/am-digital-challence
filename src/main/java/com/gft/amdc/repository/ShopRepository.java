package com.gft.amdc.repository;

import com.gft.amdc.domain.Coordinates;
import com.gft.amdc.domain.Shop;

/**
 * Created by RLBO on 24/04/2017.
 */
public interface ShopRepository {

    public Shop create (Shop shop);

    public Shop retrieve (String name);

    public Shop getNearestShop(Coordinates coordinates);

}
