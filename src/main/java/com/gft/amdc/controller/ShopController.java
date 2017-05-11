package com.gft.amdc.controller;

import com.gft.amdc.domain.Coordinates;
import com.gft.amdc.domain.Shop;
import com.gft.amdc.domain.resource.ShopResource;
import com.gft.amdc.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

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
    @RequestMapping(method = RequestMethod.GET, value = "/shops/{name}")
    public HttpEntity<ShopResource> shop(
            @PathVariable(value = "name", required = true) String name) {

        Shop shop = shopService.retrieve(name);

        if (shop == null)
            return new ResponseEntity<ShopResource>((ShopResource) null, HttpStatus.NOT_FOUND);

        ShopResource shopResource = new ShopResource(shop);
        return new ResponseEntity<ShopResource>(shopResource, HttpStatus.OK);
    }

    /**
     * Operation to add a new shop
     * The shop is
     *
     * @param shop
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/shops")
    public HttpEntity<ShopResource> shop(@Valid @RequestBody Shop shop) {

        Shop oldShop = shopService.create(shop);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(new ShopResource(shop).getLink(Link.REL_SELF).getHref()));

        if (oldShop == null) {
            return new ResponseEntity<ShopResource>(headers, HttpStatus.CREATED);
        } else {
            ShopResource oldShopResource = new ShopResource(oldShop);
            return new ResponseEntity<ShopResource>(oldShopResource, headers, HttpStatus.OK);
        }

    }

    /**
     * This operation finds the nearest shop for given longitude and latitude
     *
     * @param longitude
     * @param latitude
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/shops/nearestShop")
    public HttpEntity<ShopResource> shop(
            @RequestParam(value = "latitude", required = true) Double latitude,
            @RequestParam(value = "longitude", required = true) Double longitude) {

        Shop nearestShop = shopService.getNearestShop(new Coordinates(latitude, longitude));

        if (nearestShop == null)
            return new ResponseEntity<ShopResource>((ShopResource) null, HttpStatus.NOT_FOUND);

        ShopResource nearestShopResource = new ShopResource(nearestShop);
        return new ResponseEntity<ShopResource>(nearestShopResource, HttpStatus.OK);
    }


}
