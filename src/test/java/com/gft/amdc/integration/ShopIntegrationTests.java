/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gft.amdc.integration;

import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

import static org.assertj.core.api.Assertions.assertThat;

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

        URL url = new URL("http://localhost:" + this.port + "/shop");

        Callable<String> putShop1 = () -> {
            return put(url, payloadShop1);
        };

        Callable<String> putShop2 = () -> {
            return put(url, payloadShop2);
        };

        ExecutorService executor = Executors.newFixedThreadPool(2);
        String putShopResponse1 = executor.submit(putShop1).get();
        String putShopResponse2 = executor.submit(putShop2).get();

        //Assert that the information of the added shop is returned correctly
        String putShopNumber1 = JsonPath.read(putShopResponse1, "$.createdShop.shopAddress.number");
        assertThat(putShopNumber1).isEqualTo(SHOP_NUMBER);
        //Assert that the information of the added shop is returned correctly
        String putShopNumber2 = JsonPath.read(putShopResponse2, "$.createdShop.shopAddress.number");
        assertThat(putShopNumber2).isEqualTo(SHOP_NUMBER_2);

        //Assert that if two users submit a shop at the same time, exactly one of them gets information about replacing another version of the shop.
        assertThat(JsonPath.read(putShopResponse1, "$.updatedShop") == null || JsonPath.read(putShopResponse2, "$.updatedShop") == null);
        assertThat(!(JsonPath.read(putShopResponse1, "$.updatedShop") == null && JsonPath.read(putShopResponse2, "$.updatedShop") == null));

    }

    public static String put(URL url, String payload) throws ProtocolException, IOException {

        System.out.println(url);
        System.out.println(payload);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
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