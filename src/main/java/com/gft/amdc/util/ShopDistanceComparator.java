import com.gft.amdc.domain.Coordinates;
import com.gft.amdc.domain.Shop;

import java.util.Comparator;

public class ShopDistanceComparator implements Comparator<Shop> {

    public Coordinates coordinates;

    public ShopDistanceComparator(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public int compare(Shop s1, Shop s2) {
        return s1.distance(coordinates).compareTo(s2.distance(coordinates));
    }
}
