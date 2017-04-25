package com.gft.amdc.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

public class Shop extends ResourceSupport {

    private String shopName;
    private ShopAddress shopAddress;

    @JsonCreator
    public Shop(@JsonProperty("shopName") String shopName,
                @JsonProperty("shopAddress") ShopAddress shopAddress) {
        super();
        this.shopName = shopName;
        this.shopAddress = shopAddress;
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

    public Double distance(Coordinates coordinates) {

        return shopAddress.distance(coordinates);

    }

}
