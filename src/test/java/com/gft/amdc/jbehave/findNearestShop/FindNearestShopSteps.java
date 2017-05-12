package com.gft.amdc.jbehave.findNearestShop;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.ValidatableResponse;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;

/**
 * Created by RLBO on 08/05/2017.
 */
public class FindNearestShopSteps {


    public FindNearestShopSteps(int port) {
        RestAssured.port = Integer.valueOf(port);
    }

    private ThreadLocal<ValidatableResponse> nearestShop = new ThreadLocal<ValidatableResponse>();

    @Given("the shops $shopsTable")
    public void theShops(ExamplesTable ShopsTable) {
        for (Map<String, String> row : ShopsTable.getRows()) {

            String payLoad =
                    "{" +
                            "\"shopName\": \"" + row.get("name") + "\"," +
                            "\"shopAddress\": {" +
                            "\"number\": \"" + row.get("number") + "\"," +
                            "\"postCode\": \"" + row.get("postCode") + "\"" +
                            "}" +
                            "}";

            RestAssured.given()
                    .contentType("application/json")
                    .body(payLoad)
                    .when().post("shops").then()
                    .statusCode(201);
        }
    }

    @When("a customer looks for the nearest shop to latitude $latitude longitude $longitude")
    public void aCustomerLooksForTheNearestShop(@Named("latitude") String latitude,
                                                @Named("longitude") String longitude) {
        nearestShop.set(RestAssured.get("shops/nearestShop?latitude=" + latitude + "&longitude=" + longitude).then());
    }

    @Then("the shop $shopName is returned")
    public void theShopLondonIsReturned(@Named("shopName") String shopName) {
        nearestShop.get().body("shopName", is(shopName));
    }
}
