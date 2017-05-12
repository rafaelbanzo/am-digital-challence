package com.gft.amdc.jbehave.addShop;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.hamcrest.CoreMatchers.is;

/**
 * Created by RLBO on 08/05/2017.
 */
public class AddShopSteps {


    public AddShopSteps(int port) {
        RestAssured.port = Integer.valueOf(port);
    }

    String payLoad;
    Response response;


    @Given("a shop with name <name> number <number> and postCode <postCode>")
    public void aShop(@Named("name") String name,
                      @Named("number") String number,
                      @Named("postCode") String postCode) {
        payLoad =
                "{" +
                        "\"shopName\": \"" + name + "\"," +
                        "\"shopAddress\": {" +
                        "\"number\": \"" + number + "\"," +
                        "\"postCode\": \"" + postCode + "\"" +
                        "}" +
                        "}";

    }

    @When("the shop owner adds a shop")
    public void theShopOwnerAddsAShop() {
        response = RestAssured.given()
                .contentType("application/json")
                .body(payLoad)
                .when().post("shops");

        response.then().statusCode(201);
    }

    @Then("the shop can be obtained with the location link and the postCode is <postCode>")
    public void theShopCanBeObtainedWithTheLocationLink(@Named("postCode") String postCode) {

        String location = response.header("Location");
        assert (location != null);

        //Getting the shop with the link
        ValidatableResponse response = RestAssured.get(location).then();
        response.body("shopAddress.postCode", is(postCode));

    }
}
