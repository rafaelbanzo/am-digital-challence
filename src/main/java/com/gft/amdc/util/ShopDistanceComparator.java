package com.gft.amdc.util;

import com.gft.amdc.domain.Coordinates;
import com.gft.amdc.domain.Shop;

import java.util.Comparator;

public class ShopDistanceComparator implements Comparator<Shop> {

    public Coordinates coordinates;

    public ShopDistanceComparator(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public int compare(Shop s1, Shop s2) {
        return distanceToCoordinates(s1).compareTo(distanceToCoordinates(s2));
    }


    public Double distanceToCoordinates(Shop shop) {

        //If the coordinates of the shop are null,
        //return the longest possible distance
        if (shop.getShopAddress() == null || shop.getShopAddress().getCoordinates() == null)
            return Double.MAX_VALUE;

        Double lat1 = coordinates.getLatitude();
        Double lon1 = coordinates.getLongitude();
        Double lat2 = shop.getShopAddress().getCoordinates().getLatitude();
        Double lon2 = shop.getShopAddress().getCoordinates().getLongitude();

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
