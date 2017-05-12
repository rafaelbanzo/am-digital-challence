package com.gft.amdc.jbehave.addShopTwice;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.fail;

/**
 * Created by RLBO on 08/05/2017.
 */
public class AddShopTwiceSteps {

    public AddShopTwiceSteps(int port) {
        RestAssured.port = Integer.valueOf(port);
    }

    final static String SHOP_NAME = "testName";
    final static String SHOP_POST_CODE_1 = "NG2 1RT";
    final static String SHOP_POST_CODE_2 = "NG2 5JD";

    final static String SHOP_NUMBER_1 = "1";
    final static String SHOP_NUMBER_2 = "2";

    String payLoad1;
    String payLoad2;

    Response response1;
    Response response2;

    @Given("two shops with the same name")
    public void twoShops() {

        payLoad1 =
                "{" +
                        "\"shopName\": \"" + SHOP_NAME + "\"," +
                        "\"shopAddress\": {" +
                        "\"number\": \"" + SHOP_NUMBER_1 + "\"," +
                        "\"postCode\": \"" + SHOP_POST_CODE_1 + "\"" +
                        "}" +
                        "}";

        payLoad2 = "{" +
                "\"shopName\": \"" + SHOP_NAME + "\"," +
                "\"shopAddress\": {" +
                "\"number\": \"" + SHOP_NUMBER_2 + "\"," +
                "\"postCode\": \"" + SHOP_POST_CODE_2 + "\"" +
                "}" +
                "}";

    }

    @When("two users add shops with the same name")
    public void twoUsersAddShops() {

        Callable<Response> putShop1 = () -> {
            return RestAssured.given()
                    .contentType("application/json")
                    .body(payLoad1)
                    .when().post("shops");
        };

        Callable<Response> putShop2 = () -> {
            return RestAssured.given()
                    .contentType("application/json")
                    .body(payLoad2)
                    .when().post("shops");
        };

        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            response1 = executor.submit(putShop1).get();
            response2 = executor.submit(putShop2).get();
        } catch (InterruptedException e) {
            fail("Error adding shop " + e.getMessage());
        } catch (ExecutionException e) {
            fail("Error adding shop " + e.getMessage());
        }

    }

    @Then("one shop is added then it is updated")
    public void oneShopAddedThenUpdated() {

        if (response1.statusCode() == 201) {
            assert (response2.statusCode() == 200);
        } else if (response2.statusCode() == 201) {
            assert (response1.statusCode() == 200);
        } else {
            fail("Wrong response status");
            return;
        }

        //TODO: Validate that the updated shop is correct

    }

}
