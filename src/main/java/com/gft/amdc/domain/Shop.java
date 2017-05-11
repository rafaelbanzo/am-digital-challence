package com.gft.amdc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.time.Instant;

public class Shop {

    @NotNull
    private String shopName;

    @NotNull
    private ShopAddress shopAddress;

    @JsonIgnore
    private Instant timeStamp;

    public Shop() {
        super();
        timeStamp = Instant.now();
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public ShopAddress getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(ShopAddress shopAddress) {
        this.shopAddress = shopAddress;
    }

    public Instant getTimeStamp() { return timeStamp; }
}
