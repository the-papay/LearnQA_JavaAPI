package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class LongRedirectTest {

    private final String URL = "https://playground.learnqa.ru/api/long_redirect";

    @Test
    public void testFindLongRedirectLocationTest() {

        String locationHeader = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get(URL)
                .getHeader("Location");

        System.out.println(locationHeader);
    }

    @Test
    public void testLongRedirectCalculateRedirectsTest() {

        String locationHeader = getResponseWithInitialLocationHeader().getHeader("Location");
        int statusCode = getResponseWithInitialLocationHeader().getStatusCode();

        System.out.println("Location -> " + locationHeader);
        System.out.println("Status code -> " + statusCode);


        while (statusCode != 200) {

            Response response = RestAssured
                    .given()
                    .when()
                    .redirects()
                    .follow(false)
                    .get(locationHeader)
                    .andReturn();

            if (locationHeader == null) {
                System.out.println("Status code -> " + statusCode);
            } else {
                locationHeader = response.getHeader("Location");
                statusCode = response.getStatusCode();
                System.out.println("Location -> " + locationHeader);
            }
            System.out.println("Status code " + statusCode);
        }
    }

    public Response getResponseWithInitialLocationHeader() {

        Response responseWithInitialLocationHeader = RestAssured
                .given()
                    .redirects()
                    .follow(false)
                .when()
                .get(URL)
                .andReturn();

        return responseWithInitialLocationHeader;
    }
}

