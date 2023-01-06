import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

public class LongRedirectTest {

    @Test
    public void testLongRedirectTest() {

        String xHostHeader = RestAssured
                .get("https://playground.learnqa.ru/api/long_redirect")
                .getHeader("X-Host");

        System.out.println(xHostHeader);
    }
}
