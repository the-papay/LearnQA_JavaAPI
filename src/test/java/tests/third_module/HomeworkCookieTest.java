package tests.third_module;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HomeworkCookieTest {

    private static final String URL = "https://playground.learnqa.ru/api/homework_cookie";

    @Test
    public void testHomeworkCookieTest() {
        Response response = RestAssured
                .given()
                .when()
                .get(URL)
                .andReturn();

        if (!response.getCookies().isEmpty()) {
            Assertions.assertTrue(response.getCookie("HomeWork").equals("hw_value"));
        } else {
            System.out.println("Cookies is missing in response");
        }
    }
}
