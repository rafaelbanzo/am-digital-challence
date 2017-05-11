package com.gft.amdc.domain;

public class Coordinates {

    private double longitude;
    private double latitude;

    public Coordinates () {
        super();
    }

    public Coordinates(double latitude,
                       double longitude) {
        this();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }


}
