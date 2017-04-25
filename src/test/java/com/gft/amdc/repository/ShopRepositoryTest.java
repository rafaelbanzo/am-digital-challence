package com.gft.amdc.repository;

import com.gft.amdc.Application;
import com.gft.amdc.domain.Shop;
import com.gft.amdc.domain.ShopAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by RLBO on 21/04/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = AnnotationConfigContextLoader.class)
public class ShopRepositoryTest {

    final static String SHOP_NAME = "name";
    final static String SHOP_NUMBER = "1";
    final static String SHOP_POST_CODE = "NG2 1RT";

    @Autowired
    ApplicationContext context;

    @Autowired
    ShopRepository shopRepository;

    @Test
    public void create() throws Exception {

        //Submit a shop
        Shop shop1 = new Shop(SHOP_NAME, new ShopAddress(SHOP_NUMBER, SHOP_POST_CODE));
        shopRepository.create(shop1);

        //Test that the data in the pseudo repository is what had been inserted
        Shop createdShop = shopRepository.retrieve(shop1.getShopName());
        assertThat("shop name", createdShop.getShopName(), is(SHOP_NAME));
        assertThat("shop number", createdShop.getShopAddress().getNumber(), is(SHOP_NUMBER));
        assertThat("shop postcode", createdShop.getShopAddress().getPostCode(), is(SHOP_POST_CODE));

    }

    @Test
    public void getNearestShop() throws Exception {
    }

    @Test
    public void retrieve() throws Exception {
    }

}