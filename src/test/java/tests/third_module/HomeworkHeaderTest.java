package tests.third_module;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HomeworkHeaderTest {

    private static final String URL = "https://playground.learnqa.ru/api/homework_header";

    @Test
    public void testHomeworkHeaderTest() {
        Response response = RestAssured
                .given()
                .when()
                .get(URL)
                .andReturn();

        Assertions.assertTrue((response.getHeader("x-secret-homework-header")
                .equals("Some secret value")),"Header is missing");
    }
}
