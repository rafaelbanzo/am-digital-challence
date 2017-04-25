package com.gft.amdc.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

public class ShopAddress extends ResourceSupport {

    private String number;
    private String postCode;

    //Non JSON fields
    private Coordinates coordinates;

    @JsonCreator
    public ShopAddress(@JsonProperty("number") String number,
                       @JsonProperty("postCode") String postCode) {
        super();
        this.number = number;
        this.postCode = postCode;
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

    public Double distance(Coordinates coordinates2) {

        //If any of the parameters is null,
        //Return the longest possible distance
        if (coordinates == null || coordinates2 == null)
            return Double.MAX_VALUE;

        Double lat1 = coordinates.getLatitude();
        Double lat2 = coordinates2.getLatitude();
        Double lon1 = coordinates2.getLongitude();
        Double lon2 = coordinates.getLongitude();

        //Functionality to calculate the distance copied from
        //http://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude-what-am-i-doi
        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        return distance;
    }


}
