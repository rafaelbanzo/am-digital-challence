package com.gft.amdc.domain.resource;

import com.gft.amdc.controller.ShopController;
import com.gft.amdc.domain.Shop;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by RLBO on 02/05/2017.
 */
public class ShopResource extends Resource<Shop> {

    /**
     * Builds a shop resource and creates a link to it
     *
     * @param shop
     */
    public ShopResource(Shop shop) {
        super(shop);
        createLink();
    }

    private void createLink() {

        add(linkTo(methodOn(ShopController.class).shop(getContent().getShopName())).withSelfRel());
    }

}
