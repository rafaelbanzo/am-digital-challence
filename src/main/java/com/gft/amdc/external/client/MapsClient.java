package com.gft.amdc.external.client;

import com.gft.amdc.domain.Coordinates;
import com.gft.amdc.domain.Shop;

import java.util.concurrent.CompletableFuture;

/**
 * Created by RLBO on 11/05/2017.
 */
public interface MapsClient {

    public CompletableFuture<Coordinates> obtainCoordinates(Shop shop);

}
