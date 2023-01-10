import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TokenTest {

    final String url = "https://playground.learnqa.ru/ajax/api/longtime_job";
    String token = "";
    int taskCompletionTime = 0;

    @Test
    public void testCreateNewTask() throws InterruptedException {

        createNewTaskAndGetTimeAndToken();
        isNewTaskCreated();
        Thread.sleep(taskCompletionTime * 1000);
        checkTaskIsFinished();
    }

    public void createNewTaskAndGetTimeAndToken() {

        JsonPath response = RestAssured
                .get(url)
                .jsonPath();

        token = response.get("token");
        taskCompletionTime = response.get("seconds");
    }

    public void isNewTaskCreated() {

        JsonPath response = RestAssured
                .given()
                    .param("token", token)
                .when()
                    .get(url)
                .jsonPath();

        Assertions.assertEquals("Job is NOT ready",
                response.get("status"), "Job status is incorrect");
    }

    public void checkTaskIsFinished() {

        JsonPath response = RestAssured
                .given()
                    .param("token", token)
                .when()
                    .get(url)
                .jsonPath();

        Assertions.assertTrue(response.get("status").equals("Job is ready"),
                "Finish Status is incorrect");
        System.out.println("Verifiable status is... " + response.get("status"));
        Assertions.assertNotNull(response.get("result"),
                "Result field is missing");
        System.out.println("Verifiable result is... " + response.get("result"));
    }
}
