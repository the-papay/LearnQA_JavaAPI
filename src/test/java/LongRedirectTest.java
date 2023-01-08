import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class LongRedirectTest {

    @Test
    public void testFindLongRedirectLocationTest() {

        String locationHeader = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .getHeader("Location");

        System.out.println(locationHeader);
    }

    @Test
    public void testLongRedirectUntil200OkReturnedTest() {

        //todo: Наша задача — написать цикл, которая будет создавать запросы в цикле,
        // каждый раз читая URL для редиректа из нужного заголовка. И так, пока мы не дойдем до ответа с кодом 200.
        int statusCode = 0;
        int requestCounter = 0;

        while (statusCode != 200) {
            String locationHeader = RestAssured
                    .given()
                    .redirects()
                    .follow(true)
                    .when()
                    .get("https://playground.learnqa.ru/api/long_redirect")
                    .getHeader("Location");

            statusCode = RestAssured
                    .get(locationHeader)
                    .getStatusCode();

            requestCounter++;
        }
        System.out.println(requestCounter);
    }
}
