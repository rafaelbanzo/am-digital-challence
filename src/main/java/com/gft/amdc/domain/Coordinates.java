package com.gft.amdc.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

public class Coordinates extends ResourceSupport {

    private double longitude;
    private double latitude;

    @JsonCreator
    public Coordinates(@JsonProperty("longitude") double longitude,
                       @JsonProperty("latitude") double latitude) {
        super();
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }


}
