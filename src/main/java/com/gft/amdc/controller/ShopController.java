package com.gft.amdc.controller;

import com.gft.amdc.domain.Coordinates;
import com.gft.amdc.domain.PutShopResponse;
import com.gft.amdc.domain.Shop;
import com.gft.amdc.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class ShopController {

    @Autowired
    ShopService shopService;

    /**
     * Operation to retrieve a shop
     *
     * @param name The name of the shop to retrieve
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/shop")
    public HttpEntity<Shop> shop(
            @RequestParam(value = "name", required = true) String name) {

        Shop shop = shopService.retrieve(name);

        if (shop == null)
            return new ResponseEntity<Shop>((Shop) null, HttpStatus.NOT_FOUND);

        shop.add(linkTo(methodOn(ShopController.class).shop(shop.getShopName())).withSelfRel());
        return new ResponseEntity<Shop>(shop, HttpStatus.OK);
    }

    /**
     * Operation to add a new shop
     *
     * @param shop
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/shop")
    public HttpEntity<PutShopResponse> shop(@RequestBody Shop shop) {

        //Return error if the postcode is null
        if (shop.getShopAddress() == null || shop.getShopAddress().getPostCode() == null)
            return new ResponseEntity<PutShopResponse>((PutShopResponse)null, HttpStatus.BAD_REQUEST);

        Shop oldShop = shopService.create(shop);

        //Remove the links of the old shop
        if (oldShop != null)
            oldShop.removeLinks();

        shop.add(linkTo(methodOn(ShopController.class).shop(shop.getShopName())).withSelfRel());

        PutShopResponse response = new PutShopResponse(shop, oldShop);

        return new ResponseEntity<PutShopResponse>(response, HttpStatus.OK);
    }

    /**
     * This operation finds the nearest shop for given longitude and latitude
     * @param longitude
     * @param latitude
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/nearestShop")
    public HttpEntity<Shop> shop(@RequestParam(value = "longitude", required = true) Double longitude,
                                 @RequestParam(value = "latitude", required = true) Double latitude) {

        Shop nearestShop = shopService.getNearestShop(new Coordinates(longitude, latitude));

        if (nearestShop == null)
            return new ResponseEntity<Shop>((Shop) null, HttpStatus.NOT_FOUND);

        nearestShop.add(linkTo(methodOn(ShopController.class).shop(nearestShop.getShopName())).withSelfRel());
        return new ResponseEntity<Shop>(nearestShop, HttpStatus.OK);
    }
}
