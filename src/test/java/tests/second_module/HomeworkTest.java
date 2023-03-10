package tests.second_module;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeworkTest {

    @Test
    public void testShowSecondHomeworkMessage() {

        JsonPath response = RestAssured
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();

            ArrayList<HashMap> messageList = response.get("messages");
            System.out.println(messageList.get(1).get("message"));

    }
}
