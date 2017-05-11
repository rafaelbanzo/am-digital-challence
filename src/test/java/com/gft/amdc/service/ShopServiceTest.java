package com.gft.amdc.service;

import com.gft.amdc.Application;
import com.gft.amdc.domain.Coordinates;
import com.gft.amdc.domain.Shop;
import com.gft.amdc.domain.ShopAddress;
import com.gft.amdc.external.client.MapsClient;
import com.gft.amdc.repository.ShopRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Arrays;

import static org.mockito.Mockito.when;

//TODO: Check whether it is necessary to add these annotations
@RunWith(SpringJUnit4ClassRunner.class)
public class ShopServiceTest {


    @Mock
    private ShopRepository shopsMock;

    @Mock
    private MapsClient mapsClientMock;

    @InjectMocks
    private ShopService shopService = new ShopService();

    private Shop shop1;
    private Shop shop2;


    @Before
    public void initialise() {
        Coordinates coordinates1 = new Coordinates(52.6686509, -1.0882725);
        ShopAddress shopAddress1 = new ShopAddress();
        shopAddress1.setCoordinates(coordinates1);
        shopAddress1.setPostCode("LE4 9JN");
        shop1 = new Shop();
        shop1.setShopAddress(shopAddress1);
        shop1.setShopName("shop Leicester LE4 9JN");


        Coordinates coordinates2 = new Coordinates(51.5734292, -0.0716026);
        ShopAddress shopAddress2 = new ShopAddress();
        shopAddress2.setCoordinates(coordinates2);
        shopAddress2.setPostCode("E5 9AQ");
        shop2 = new Shop();
        shop2.setShopAddress(shopAddress2);
        shop2.setShopName("shop London E5 9AQ");
    }

    @Test
    public void getNearestShop() {

        when(shopsMock.findAll())
                .thenReturn(Arrays.asList(new Shop[]{(Shop) shop1, shop2}));

        Coordinates coordinates = new Coordinates(51.5074, 0.1278);
        Shop nearestShop = shopService.getNearestShop(coordinates);

        assert (nearestShop.getShopName().equals(shop2.getShopName()));

    }

}