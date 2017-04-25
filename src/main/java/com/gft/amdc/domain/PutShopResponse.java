package com.gft.amdc.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by RLBO on 20/04/2017.
 */
public class PutShopResponse {
    Shop createdShop;
    Shop updatedShop;

    @JsonCreator
    public PutShopResponse(@JsonProperty("createdShop") Shop createdShop,
                           @JsonProperty("updatedShop") Shop updatedShop) {
        super();
        this.createdShop = createdShop;
        this.updatedShop = updatedShop;
    }

    public Shop getCreatedShop() {
        return createdShop;
    }

    public void setCreatedShop(Shop createdShop) {
        this.createdShop = createdShop;
    }

    public Shop getUpdatedShop() {
        return updatedShop;
    }

    public void setUpdatedShop(Shop updatedShop) {
        this.updatedShop = updatedShop;
    }
}
