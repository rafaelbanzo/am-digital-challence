package com.gft.amdc.repository;

import com.gft.amdc.domain.Shop;

import java.util.Collection;

/**
 * Created by RLBO on 24/04/2017.
 */
public interface ShopRepository {

    public Shop create(Shop shop);

    public Shop retrieve(String name);

    public Collection<Shop> findAll();

    public Shop update(Shop shop);
}
