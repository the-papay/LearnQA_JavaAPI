package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PasswordGuessingTest {

    private final String SECRET_PASSWORD_URL = "https://playground.learnqa.ru/ajax/api/get_secret_password_homework";
    private final String CHECK_AUTH_COOKIE_URL = "https://playground.learnqa.ru/ajax/api/check_auth_cookie";
    private final String LOGIN = "super_admin";
    private String authCookie;
    private String authStatus;
    private List<String> passwordList;

    @Test
    public void testPasswordGuessingTest() {

        preparePasswordList();

        for (int i = 0; i < passwordList.size(); i++) {

            if (!checkAuthCookie(getAuthCookie(passwordList.get(i))).equals("You are NOT authorized")) {
                System.out.println("login " + LOGIN);
                System.out.println("password " + passwordList.get(i));
            }
        }
    }

    public List<String> preparePasswordList() {

        passwordList = new ArrayList<>();
        String[] passwords = new String[] {"password" ,"123456" ,"123456789" ,"12345678" ,"12345" ,"qwerty" ,"abc123" ,
                "football" ,"1234567" ,"monkey" ,"111111" ,"letmein" ,"1234" ,"1234567890" ,"dragon" ,"baseball" ,
                "sunshine" ,"iloveyou" ,"trustno1" ,"princess" ,"adobe123" ,"123123" ,"welcome" ,"login" ,"admin" ,
                "qwerty123" ,"solo" ,"1q2w3e4r" ,"master" ,"666666" ,"photoshop" ,"1qaz2wsx" ,"qwertyuiop" ,"ashley" ,
                "mustang" ,"121212" ,"starwars" ,"654321" ,"bailey" ,"access" ,"flower" ,"555555" ,"passw0rd" ,
                "shadow" ,"lovely" ,"7777777" ,"michael" ,"!@#$%^&*" ,"jesus" ,"password1" ,"superman" ,"hello",
                "charlie" ,"888888" ,"696969" ,"hottie" ,"freedom" ,"aa123456" ,"qazwsx" ,"ninja" ,"azerty",
                "loveme" ,"whatever" ,"donald" ,"batman" ,"zaq1zaq1" ,"Football" ,"000000" ,"123qwe"};

        for (int i = 0; i < passwords.length - 1; i++) {
            passwordList.add(passwords[i]);
        }

        return passwordList;
    }

    public String getAuthCookie(String password) {

        Map<String, String> payload = new HashMap<>();
        payload.put("login", LOGIN);
        payload.put("password", password);

        Response response = RestAssured
                .given()
                    .body(payload)
                .when()
                    .post(SECRET_PASSWORD_URL)
                .andReturn();

        authCookie = response.getCookie("auth_cookie");

        return authCookie;

    }

    public String checkAuthCookie(String authCookie) {

        Response response = RestAssured
                .given()
                    .cookie("auth_cookie", authCookie)
                .when()
                    .get(CHECK_AUTH_COOKIE_URL)
                .andReturn();

        authStatus = response.getBody().asString();

        return authStatus;

    }

}
