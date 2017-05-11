//TODO: DELETE



package com.gft.amdc.integration;

import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ShopIntegrationTests {

    @LocalServerPort
    private int port;

    final static String SHOP_NAME = "name";
    final static String SHOP_POST_CODE = "NG2 1RT";

    final static String SHOP_NUMBER = "1";
    final static String SHOP_NUMBER_2 = "2";

    String payloadShop1 = "{" +
            "\"shopName\": \"" + SHOP_NAME + "\"," +
            "\"shopAddress\": {" +
            "\"number\": \"" + SHOP_NUMBER + "\"," +
            "\"postCode\": \"" + SHOP_POST_CODE + "\"" +
            "}" +
            "}";

    String payloadShop2 = "{" +
            "\"shopName\": \"" + SHOP_NAME + "\"," +
            "\"shopAddress\": {" +
            "\"number\": \"" + SHOP_NUMBER_2 + "\"," +
            "\"postCode\": \"" + SHOP_POST_CODE + "\"" +
            "}" +
            "}";

    @Test
    public void parallelRequests() throws Exception {

        URL url = new URL("http://localhost:" + this.port + "/shops");

        Callable<String> putShop1 = () -> {
            return post(url, payloadShop1);
        };

        Callable<String> putShop2 = () -> {
            return post(url, payloadShop2);
        };

        ExecutorService executor = Executors.newFixedThreadPool(2);
        String putShopResponse1 = executor.submit(putShop1).get();
        String putShopResponse2 = executor.submit(putShop2).get();

        //Assert that if two users submit a shop at the same time, exactly one of them gets information about replacing another version of the shop.
        assertTrue(StringUtils.isEmpty(putShopResponse1) || StringUtils.isEmpty(putShopResponse2));
        assertTrue(!(StringUtils.isEmpty(putShopResponse1) && StringUtils.isEmpty(putShopResponse2)));

        //Assert that the name and number of the old shop is correct
        if (!StringUtils.isEmpty(putShopResponse2)) {
            String oldShopName = JsonPath.read(putShopResponse2, "$.shopName");
            String oldShopNumber = JsonPath.read(putShopResponse2, "$.shopAddress.number");
            assertThat(oldShopName, is(SHOP_NAME));
            assertThat(oldShopNumber, is(SHOP_NUMBER));
        }
        if (!StringUtils.isEmpty(putShopResponse1)) {
            String oldShopName = JsonPath.read(putShopResponse1, "$.shopName");
            String oldShopNumber = JsonPath.read(putShopResponse1, "$.shopAddress.number");
            assertThat(oldShopName, is(SHOP_NAME));
            assertThat(oldShopNumber, is(SHOP_NUMBER_2));
        }

    }

    public static String post(URL url, String payload) throws ProtocolException, IOException {

        System.out.println(url);
        System.out.println(payload);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
        osw.write(payload);
        osw.flush();
        osw.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
