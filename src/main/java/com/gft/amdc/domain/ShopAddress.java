package com.gft.amdc.domain;


import javax.validation.constraints.NotNull;

public class ShopAddress {

    @NotNull
    private String number;

    @NotNull
    private String postCode;

    private Coordinates coordinates;

    public ShopAddress() {
        super();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

}
